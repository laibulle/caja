(ns account.use-cases.delete-user-use-case
  (:require
   [common.interface :refer [=> collect-result]]
   [account.infrastructure.jwt :refer [generate-account-token]]
   [account.domain.account :refer [validate-login-input]]
   [postgres-db.interface :as db]
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [next.jdbc :as jdbc]
   [password-hash.interface :as ph]))

(defn- validate-input [input] input)

(defn- delete-user [input] input)

(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:input input :tx tx}
        (validate-input)
        (=> delete-user))))

(comment)