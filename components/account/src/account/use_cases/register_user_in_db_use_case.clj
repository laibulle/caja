(ns account.use-cases.register-user-in-db-use-case
  (:require
   [malli.core :as m]
   [malli.clj-kondo :as mc]
   [messages.interface :as mi]
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [common.interface :refer [=> collect-result ErrorSchema]]
   [account.domain.user :as user]
   [password-hash.interface :as ph])
  (:import java.sql.Timestamp))

(defn- user-valid? [{:keys [data]}]
  (if (true? (user/validate-register-user-input data))
    {:data data}
    {:errors [:invalid-user]}))

(defn- user-exists? [{:keys [data]}]
  (if (nil? (ua/get-user-by-email (:email data)))
    {:data data}
    {:errors [:email-already-taken]}))

(defn- save-in-db [{:keys [data]}]
  (ua/insert-user data)
  {:data data})

(defn- hash-password [{:keys [data]}]
  {:data
   (-> data
       (assoc :password_hash (ph/encrypt (data :password)))
       (dissoc :password))})

(defn- credentials-provider [data]
  (or (=  (:provider data) :credentials) (=  (:provider data) nil)))

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
  (-> (mc/collect *ns*) (mc/linter-config))
  (mc/emit!)
  (user-exists? {:data {:email "hell"}})
  (execute {:name "John Doe" :email "j@gmdsail.com" :password "Noirfnefwerf#mopgmtrogmroptgm"}))