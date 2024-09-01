(ns account.use-cases.register-user-in-db-use-case
  (:require
   [malli.core :as m]
   [malli.clj-kondo :as mc]
   [app.petit-plat.email.interface :as em]
   [common.interface :refer [=> collect-result ErrorSchema]]
   [account.infrastructure.datomic-user-schema :as user-schema]
   [account.domain.user :as user]
   [password-hash.interface :as ph]))

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
                                 :variables {:title "hello" :intro [message] :outro ["outro"] :product {:name "My product" :link "http://link.com"}}})]
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