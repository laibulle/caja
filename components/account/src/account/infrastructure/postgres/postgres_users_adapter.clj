(ns account.infrastructure.postgres.postgres-users-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]
   [clojure.set :as set])
  (:import java.sql.Timestamp))


(def table-name :users)

(defn domain-user-to-db [db-user]
  (-> db-user
      (set/rename-keys {:password-hash :password_hash
                        :confirmed-at :confirmed_at
                        :confirmation-token :confirmation_token
                        :updated-at :updated_at})))


(defn db-to-domain-user [db-user]
  (-> db-user
      (set/rename-keys {:users/id :id
                        :users/name :name
                        :users/email :email
                        :users/password_hash :password-hash
                        :users/confirmed_at :confirmed-at
                        :users/confirmation_token :confirmation-token
                        :users/created_at :created-at
                        :users/updated_at :updated-at})))


(defn insert-user [data]
  (-> {:insert-into table-name
       :values [(domain-user-to-db data)]}
      (sql/format)
      (db/execute!)))

(defn get-user-by-email [email]
  (-> {:select [:id :name :email :confirmed_at :confirmation_token :created_at :updated_at]
       :from [table-name]
       :where
       [:= :email email]}
      (sql/format)
      (db/execute-one!)
      (db-to-domain-user)))

(comment
  (insert-user {:name "hello" :email "test@gmail.com" :confirmed-at (Timestamp. (System/currentTimeMillis))})
  (get-user-by-email "test@gmail.com"))