(ns account.infrastructure.postgres.postgres-users-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]
   [clojure.set :as set]
   [next.jdbc :as jdbc]))

(def table-name :users)

(def attributes [:id :name :email :password_hash :confirmed_at :confirmation_token :created_at :updated_at])

(defn domain-user-to-db [user]
  (-> user
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


(defn update-user [tx data]
  (let [query {:update table-name
               :set (domain-user-to-db data)
               :where [:and [:= :id (:id data)] [:nil :deleted_at]]}
        res (db/execute-one! tx (sql/format query) {:return-keys true})]
    (db-to-domain-user res)))

(defn insert-user [tx data]
  (let [query {:insert-into table-name
               :values [(domain-user-to-db data)]}
        res (db/execute-one! tx (sql/format query) {:return-keys true})]
    (db-to-domain-user res)))

(defn get-user-by-email [tx email]
  (->> {:select attributes
        :from [table-name]
        :where
        [:and [:= :email email] [:nil :deleted_at]]}
       (sql/format)
       (db/execute-one! tx)
       (db-to-domain-user)))

(defn get-user-by-id [tx id]
  (->> {:select attributes
        :from [table-name]
        :where
        [:and [:= :id id] [:nil :deleted_at]]}
       (sql/format)
       (db/execute-one! tx)
       (db-to-domain-user)))

(comment
  (jdbc/with-transaction [tx @db/datasource]
    ;(insert-user tx {:name "hello" :email "j@gmdsail.com" :confirmed-at (Timestamp. (System/currentTimeMillis))})
    (get-user-by-email tx "j@gmdsail.com")
    (update-user tx {:id 1 :email "jj@mjk.fr"})))