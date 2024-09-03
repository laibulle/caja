(ns account.infrastructure.postgres.postgres-password-reset-request-adapter
  (:require
   [postgres-db.interface :as db]
   [honey.sql :as sql]
   [clojure.set :as set]
   [next.jdbc :as jdbc])
  (:import [java.time ZoneId ZonedDateTime Duration]
           [java.sql Timestamp]))

(def table-name :users_password_reset_requests)

(defn domain-request-to-db [user]
  (-> user
      (set/rename-keys {:user-id :user_id
                        :executed-at :executed_at
                        :created-at :created_at
                        :updated-at :updated_at})))

(defn db-to-domain-request [db-user]
  (-> db-user
      (set/rename-keys {:users_password_reset_requests/id :id
                        :users_password_reset_requests/token :token
                        :users_password_reset_requests/executed_at :executed-at
                        :users_password_reset_requests/created_at :created-at
                        :users_password_reset_requests/updated_at :updated-at})))

(defn update-request [tx data]
  (let [query {:update table-name
               :set (domain-request-to-db data)
               :where [:= :id (:id data)]}
        res (db/execute-one! tx (sql/format query) {:return-keys true})]
    (db-to-domain-request res)))

(defn insert-request [tx data]
  (let [query {:insert-into table-name
               :values [(domain-request-to-db data)]}
        res (db/execute-one! tx (sql/format query) {:return-keys true})]
    (db-to-domain-request res)))

(defn list-pending-requests-for-user
  [tx data]
  (let [query {:select [:id]
               :from [table-name]
               :where [:and [:= :user-id (:user-id data)]
                       [:>= :created-at (:from data)]]}
        res (db/execute! tx (sql/format query))]
    (vec (map db-to-domain-request res))))

(defn get-request-by-token [tx token]
  (->> {:select [:id :token :executed_at :created_at :updated_at]
        :from [table-name]
        :where
        [:= :token token]}
       (sql/format)

       (db/execute-one! tx)
       (db-to-domain-request)))

(comment

  (let [one-hour-ago-sql (Timestamp/from (.toInstant (.minus (ZonedDateTime/now (ZoneId/systemDefault)) (Duration/ofHours 1))))]
    (jdbc/with-transaction [tx @db/datasource]
      (list-pending-requests-for-user tx {:user-id 1 :from one-hour-ago-sql})
        ;(insert-user tx {:name "hello" :email "j@gmdsail.com" :confirmed-at (Timestamp. (System/currentTimeMillis))})
        ;(get-request-by-token tx "j@gmdsail.com")
        ;(update-request tx {:id 1 :email "jj@mjk.fr"})
      )))