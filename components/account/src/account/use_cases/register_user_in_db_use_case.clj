(ns account.use-cases.register-user-in-db-use-case
  (:require
   [malli.core :as m]
   [malli.clj-kondo :as mc]
   [messages.interface :as mi]
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [account.infrastructure.postgres.postgres-organizations-adapter :as oa]
   [account.infrastructure.postgres.postgres-memberships-adapter :as ma]
   [taoensso.tower :as tower :refer (with-tscope)]
   [common.interface :refer [=> collect-result ErrorSchema]]
   [account.domain.user :as user]
   [password-hash.interface :as ph]
   [postgres-db.interface :as db]
   [next.jdbc :as jdbc])
  (:import java.sql.Timestamp)
  (:import [java.net URLEncoder]))

(def t (tower/make-t
        {:dictionary
         {:fr   {:confirmation-email {:subject "Confirmez votre email"
                                      :intro "Bienvenue"
                                      :reset-password "Confirm email"
                                      :outro ""
                                      :instructions "Pour commencer à utiliser l'application veuillez confirmer votre email en cliquant sur le lien si dessous :"}
                 :missing  "|Missing translation: [%1$s %2$s %3$s]|"}}
         :dev-mode? true
         :fallback-locale :fr}))

(defn- random-name []
  (apply str (repeatedly 20 #(rand-nth "abcdefghijklmnopqrstuvwxyz0123456789"))))

(defn- user-valid? [input]
  (if (true? (user/validate-register-user-input (:input input)))
    input
    {:errors [:invalid-user]}))

(defn- user-exists? [input]
  (if (nil? (ua/get-user-by-email (:tx input) (get-in input [:input :email])))
    input
    {:errors [:email-already-taken]}))

(defn- save-in-db [input]
  (let [user (ua/insert-user (:tx input) (:input input))
        name (random-name)
        organization (oa/insert-organization (:tx input) {:owner-id (:id user) :name name :slug name})
        membership (ma/insert-membership (:tx input) {:role "owner" :organization-id (:id organization) :user-id (:id user)})]
    (-> input (assoc :user user)
        (assoc :user user)
        (assoc :organization organization)
        (assoc :membership membership))))

(defn- hash-password [input]
  (-> input
      (assoc-in [:input :password_hash] (ph/encrypt (get-in input [:input :password])))
      (update-in [:input] dissoc :password)))

(defn- credentials-provider [input]
  (or (= (:provider input) :credentials) (=  (:provider input) nil)))

(defn- send-confirmation-email [input]
  (if (credentials-provider (:input input))
    (with-tscope :confirmation-email
      (let [confirmation-link (mi/create-email-link (str "/confirm-email?token=" (get-in input [:input :confirmation-token]) "&email=" (URLEncoder/encode (get-in input [:input :email]) "UTF-8")))
            result (mi/send-email-from-template {:to (get-in input [:input :email])
                                                 :subject (t :fr :subject)
                                                 :variables {:title (t :fr :subject)
                                                             :intro [(t :fr :intro)]
                                                             :outro [(t :fr :outro)]
                                                             :action [{:instructions (t :fr :instructions)
                                                                       :button [{:link confirmation-link :text (t :fr :reset-password) :color "blue"}]}]}})]
        (if (true? result)
          input
          result)))
    input))

(defn- generate-user-data [input]
  (if (credentials-provider (get-in input [:input]))
    (assoc-in input [:input :confirmation-token] (user/generate-confirmation-token))
    (assoc-in input [:input :confirmed_at] (Timestamp. (System/currentTimeMillis)))))

(defn- format-response [input]
  {:data (:user input)})

(m/=>  execute [:=> [:cat user/RegisterUserInput] [:or ErrorSchema user/User]])
(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]

    (-> {:input input :tx tx}
        (=> user-valid?)
        (=> user-exists?)
        (=> generate-user-data)
        (=> hash-password)
        (=> save-in-db)
        (=> send-confirmation-email)
        (=> format-response)
        collect-result)))

(comment
  (with-tscope :confirmation-email
    (t :fr :subject))
  (random-name)
  (-> (mc/collect *ns*) (mc/linter-config))
  (mc/emit!)
  (user-exists? {:input {:email "hell"}})
  (execute {:name "John Doe" :email "dewfrefrjdew@djdewnjkreds.fr" :password "Noirfnefwerf#mopgmtrogmroptgm"}))