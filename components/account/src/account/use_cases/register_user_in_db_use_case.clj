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
  (:import java.sql.Timestamp))

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
    (let [message (str "Hello " (:name data) ". Your confirmation token is " (:confirmation-token data))
          result (mi/send-email-from-template {:to (:email data)
                                               :subject "Confirm your email"
                                               :variables {:title "hello" :intro [message] :outro ["outro"] :product {:name "My product" :link "http://link.com"}}})]
      (if (true? result)
        {:data data}
        result))
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
  (random-name)
  (-> (mc/collect *ns*) (mc/linter-config))
  (mc/emit!)
  (user-exists? {:data {:email "hell"}})
  (execute {:name "John Doe" :email "j@djnjkreds.fr" :password "Noirfnefwerf#mopgmtrogmroptgm"}))