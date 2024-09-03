(ns account.use-cases.send-reset-password-link
  (:require
   [account.infrastructure.postgres.postgres-users-adapter :as ua]
   [common.interface :refer [=> collect-result ErrorSchema]]
   [next.jdbc :as jdbc]
   [postgres-db.interface :as db]))

(def user-not-found :user-not-found)

(defn check-threshold [input]
  input)

(defn create-reset-request [input]
  input)

(defn send-email [input]
  input)

(defn get-user-by-email [input]
  (let [user (ua/get-user-by-email (:tx input) (get-in input [:input :email]))]
    (if (not (nil? user))
      (assoc input :user user)
      {:errors [user-not-found]})))


(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:input input}
        (get-user-by-email)
        (=> check-threshold)
        (=> create-reset-request)
        (=> send-email)
        collect-result)))

(comment
  (execute {:email "fnreifnre@gmail.com"}))