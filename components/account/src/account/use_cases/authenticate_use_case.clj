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

(def email-not-confirmed :email-not-confirmed)

(defn- validate-input [input]
  (if (validate-login-input (:data input))
    input
    {:errors [:invalid-input]}))

(defn- find-account-by-email [input]
  (let [user (ua/get-user-by-email (:tx input) (get-in input [:data :email]))]
    (if (nil? user)
      {:errors [invalid-credentials-error]}
      (assoc input :account user))))

(defn- check-password [input]
  (if (false? (ph/check (get-in input [:data :password]) (get-in input [:account :password-hash])))
    {:errors [invalid-credentials-error]}
    input))

(defn- check-confirmed [input]
  (if (nil? (get-in input [:account :confirmed-at]))
    {:errors [email-not-confirmed]}
    input))

(defn- create-token [input]
  {:data {:jwt (generate-account-token {:account-id (get-in input [:account :id])})}})

(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:data input :tx tx}
        (=> validate-input)
        (=> find-account-by-email)
        (=> check-password)
        (=> check-confirmed)
        (=> create-token)
        collect-result)))

(comment
  (execute {:email "jsssa@gmail.com" :password "Noirfnefwerf#mopgmtrogmroptgm"})
  (execute {:email "frefre@gmail.com" :password "ceriomfiver"}))