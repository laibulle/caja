(ns account.use-cases.authenticate-use-case
  (:require
   [common.interface :refer [=> collect-result]]
   [account.infrastructure.jwt :refer [generate-account-token]]
   [account.domain.account :refer [validate-login-input]]
   [postgres-db.interface :as db]
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [next.jdbc :as jdbc]
   [password-hash.interface :as ph]))

(def invalid-credentials-error :invalid-credentials)

(defn- validate-input [{:keys [data]}]
  (if (validate-login-input data)
    {:data data}
    {:errors [:invalid-input]}))

(defn- find-account-by-email [{:keys [data]}]
  (jdbc/with-transaction [tx @db/datasource]
    (let [user (ua/get-user-by-email tx (:email data))]
      (if (nil? nil)
        {:errors [invalid-credentials-error]}
        {:data (assoc data user :account)}))))

(defn- check-password [{:keys [data]}]
  (if (false? (ph/check (:password data) (get-in data [:account :password])))
    {:errors [invalid-credentials-error]}
    {:data data}))

(defn- create-token [{:keys [data]}]
  {:data {:jwt (generate-account-token {:account-id (get-in data [:account :id])})}})

(defn execute [input]
  (-> {:data input}
      (=> validate-input)
      (=> find-account-by-email)
      (=> check-password)
      (=> create-token)
      collect-result))

(comment
  (execute {:email "jsssa@gmail.com" :password "Noirfnefwerf#mopgmtrogmroptgm"})
  (execute {:email "frefre@gmail.com" :password "ceriomfiver"}))