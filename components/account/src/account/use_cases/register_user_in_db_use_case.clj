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

(def my-tconfig
  {:dictionary
   {:fr   {:confirmation-email {:subject         "Confirmez votre email"
                                :intro "Bienvenue"
                                :outro ""
                                :instructions "Pour commencer à utiliser l'application veuillez confirmer votre email en cliquant sur le lien si dessous :"}
           :missing  "|Missing translation: [%1$s %2$s %3$s]|"}}
   :dev-mode? true
   :fallback-locale :fr})

(def t (tower/make-t my-tconfig))

(defn- random-name []
  (apply str (repeatedly 20 #(rand-nth "abcdefghijklmnopqrstuvwxyz0123456789"))))

(defn- user-valid? [{:keys [data]}]
  (if (true? (user/validate-register-user-input data))
    {:data data}
    {:errors [:invalid-user]}))

(defn- user-exists? [{:keys [data]}]
  (jdbc/with-transaction [tx @db/datasource]
    (if (nil? (ua/get-user-by-email tx (:email data)))
      {:data data}
      {:errors [:email-already-taken]})))

(defn- save-in-db [{:keys [data]}]
  (jdbc/with-transaction [tx @db/datasource]
    (let [user (ua/insert-user tx data)
          name (random-name)
          organization (oa/insert-organization tx {:owner-id (:id user) :name name :slug name})
          membership (ma/insert-membership tx {:role "owner" :organization-id (:id organization) :user-id (:id user)})]
      {:data data :user user :organization organization :membership membership})))

(defn- hash-password [{:keys [data]}]
  {:data
   (-> data
       (assoc :password_hash (ph/encrypt (data :password)))
       (dissoc :password))})

(defn- credentials-provider [data]
  (or (= (:provider data) :credentials) (=  (:provider data) nil)))

(defn- send-confirmation-email [{:keys [data]}]
  (if (credentials-provider data)
    (with-tscope :confirmation-email
      (let [confirmation-link (mi/create-email-link (str "/confirm-email?token=" (:confirmation-token data) "&email=" (URLEncoder/encode (:email data) "UTF-8")))
            result (mi/send-email-from-template {:to (:email data)
                                                 :subject (t :fr :subject)
                                                 :variables {:title (t :fr :subject)
                                                             :intro [(t :fr :intro)]
                                                             :outro [(t :fr :outro)]
                                                             :action [{:instructions (t :fr :instructions)
                                                                       :button [{:link confirmation-link :text "Confirm email" :color "blue"}]}]}})]
        (if (true? result)
          {:data data}
          result)))
    {:data data}))

(defn- generate-user-data [{:keys [data]}]
  {:data (-> data
             (assoc :confirmed_at (when (not (credentials-provider data))  (Timestamp. (System/currentTimeMillis))))
             (assoc :confirmation-token (when (credentials-provider data) (user/generate-confirmation-token))))})

(m/=>  execute [:=> [:cat user/RegisterUserInput] [:or ErrorSchema user/User]])
(defn execute [input]
  (-> {:data input}
      (=> user-valid?)
      (=> user-exists?)
      (=> generate-user-data)
      (=> hash-password)
      (=> save-in-db)
      (=> send-confirmation-email)
      collect-result))

(comment
  (with-tscope :confirmation-email
    (t :fr :subject))
  (random-name)
  (-> (mc/collect *ns*) (mc/linter-config))
  (mc/emit!)
  (user-exists? {:data {:email "hell"}})
  (execute {:name "John Doe" :email "dewfrefrjdew@djdewnjkreds.fr" :password "Noirfnefwerf#mopgmtrogmroptgm"}))