(ns account.use-cases.delete-user-use-case
  (:require
   [common.interface :refer [=> collect-result]]
   [postgres-db.interface :as db]
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [next.jdbc :as jdbc]
   [account.domain.user :as user])
  (:import java.sql.Timestamp))

(defn- input-valid? [input]
  (if (true? (user/validate-delete-user-input (:input input)))
    input
    {:errors [:invalid-input]}))

(defn- delete-user [input]
  (let [values {:id (get-in input [:input :user-id])
                :deleted-at (Timestamp. (System/currentTimeMillis))}]
    (ua/update-user  (:tx input) values)))

(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:input input :tx tx}
        (input-valid?)
        (=> delete-user)
        (collect-result))))

(comment)