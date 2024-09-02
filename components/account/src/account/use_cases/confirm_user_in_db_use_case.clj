(ns account.use-cases.confirm-user-in-db-use-case
  (:require [account.domain.user :as user]
            [next.jdbc :as jdbc]
            [postgres-db.interface :as db]
            [common.interface :refer [=> collect-result]]
            [account.infrastructure.postgres.postgres-users-adapter :as ua])
  (:import java.sql.Timestamp))

(defn- input-valid? [input]
  (if (true? (user/validate-confirmation-email-input (:data input)))
    input
    {:errors [:invalid-confirmation-token-input]}))

(defn- get-user [input]
  (let [user (ua/get-user-by-email (:tx input) (get-in input [:data :email]))]
    (if (some? user)
      (assoc-in input [:user] user)
      {:errors [:email-confirmation-token-not-found]})))

(defn- token-valid [input]
  (if (= (get-in input [:data :token]) (get-in input [:user :confirmation-token]))
    input
    {:errors [:email-confirmation-token-not-found]}))

(defn- update-user [input]
  (let [values {:id (get-in input [:user :id])
                :confirmed_at (Timestamp. (System/currentTimeMillis))
                :confirmation-token nil}]
    (ua/update-user  (:tx input) values)
    input))

(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:data input :tx tx}
        (=> input-valid?)
        (=> get-user)
        (=> token-valid)
        (=> update-user)
        collect-result)))

(comment []
         (execute {:token "1e35f716072a4828a0dc8e60fe598f43" :email "jj@mjk.fr"}))