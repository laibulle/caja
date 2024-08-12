(ns app.petit-plat.account.use-cases.register-user-in-db-use-case
  (:require
   [malli.core :as m]
   [malli.clj-kondo :as mc]
   [app.petit-plat.email.interface :as em]
   [app.petit-plat.common.interface :refer [handle-errors collect-result ErrorSchema]]
   [app.petit-plat.account.infrastructure.datomic-user-schema :as user-schema]
   [app.petit-plat.account.domain.user :as user]
   [app.petit-plat.password-hash.interface :as ph]))

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

(defn- send-confirmation-email [{:keys [data]}]
  (let [message (str "Hello " (:name data) ". Your confirmation token is " (:confirmation-token data))
        result (em/send-message {:to (:email data)
                                 :subject "Confirm your email"
                                 :body [:alternative
                                        {:type "text/plain"
                                         :content message}
                                        {:type "text/html"
                                         :content message}]})]
    (if (true? result)
      {:data data}
      result)))

(defn- generate-user-data [{:keys [data]}]
  {:data (-> data
             (assoc :confirmed false)
             (assoc :confirmation-token (user/generate-confirmation-token)))})

(m/=>  execute [:=> [:cat user/RegisterUserInput] [:or ErrorSchema user/User]])
(defn execute [input]
  (-> {:data input}
      (handle-errors user-valid?)
      (handle-errors user-exists?)
      (handle-errors generate-user-data)
      (handle-errors hash-password)
      (handle-errors save-in-db)
      (handle-errors send-confirmation-email)
      collect-result))

(comment
  (-> (mc/collect *ns*) (mc/linter-config))
  (mc/emit!)
  (user-exists? {:data {:email "hell"}})
  (execute {:name "John Doe" :email "j@gmail.com" :password "Noirfnefwerf#mopgmtrogmroptgm"}))