(ns account.use-cases.register-user-in-db-use-case
  (:require
   [malli.core :as m]
   [malli.clj-kondo :as mc]
   [messages.interface :as mi]
   [common.interface :refer [=> collect-result ErrorSchema]]
   ;[account.infrastructure.datomic-user-schema :as user-schema]
   [account.domain.user :as user]
   [password-hash.interface :as ph]
   [clj-time.core :as t]))

(defn- user-valid? [{:keys [data]}]
  (if (true? (user/validate-register-user-input data))
    {:data data}
    {:errors [:invalid-user]}))

(defn- user-exists? [{:keys [data]}]
  (if (nil? (user-schema/get-user-by-email (:email data)))
    {:data data}
    {:errors [:email-already-taken]}))

(defn- save-in-db [{:keys [data]}]
  (user-schema/insert-user data)
  {:data data})

(defn- hash-password [{:keys [data]}]
  {:data
   (assoc data :password (ph/encrypt (data :password)))})

(defn- credentials-provider [data]
  (= (:provider data) :credentials))

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
             (assoc :confirmed_at (when (not (credentials-provider data)) (t/now)))
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
  (execute {:name "John Doe" :email "j@gmail.com" :password "Noirfnefwerf#mopgmtrogmroptgm"}))