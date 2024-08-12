(ns app.petit-plat.authentication.use-cases.authenticate-use-case
  (:require
   [app.petit-plat.common.interface :refer [handle-errors collect-result]]
   [app.petit-plat.authentication.infrastructure.jwt :refer [generate-account-token]]
   [app.petit-plat.authentication.domain.account :refer [validate-login-input]]
   [app.petit-plat.authentication.infrastructure.datomic-account-schema :refer [get-account-by-email]]
   [app.petit-plat.password-hash.interface :as ph]))

(def invalid-credentials-error :invalid-credentials)

(defn- validate-input [{:keys [data]}]
  (if (validate-login-input data)
    {:data data}
    {:errors [:invalid-input]}))

(defn- find-account-by-email [{:keys [data]}]
  (let [account (get-account-by-email (:email data))]
    (if (nil? account)
      {:errors [invalid-credentials-error]}
      {:data (assoc data account :account)})))

(defn- check-password [{:keys [data]}]
  (if (false? (ph/check (:password data) (get-in data [:account :password])))
    {:errors [invalid-credentials-error]}
    {:data data}))

(defn- create-token [{:keys [data]}]
  {:data {:jwt (generate-account-token {:account-id (get-in data [:account :id])})}})

(defn execute [input]
  (-> {:data input}
      (handle-errors validate-input)
      (handle-errors find-account-by-email)
      (handle-errors check-password)
      (handle-errors create-token)
      collect-result))

(comment
  (execute {:email "frefre@gmail.com" :password "ceriomfiver"}))