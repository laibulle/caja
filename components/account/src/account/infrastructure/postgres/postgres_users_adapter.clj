(ns account.infrastructure.postgres.postgres-users-adapter
  (:require [honey.sql :as sql]))

(defn hello [])


(defn get-user-by-email [email]
  (-> {:select [:a :b :c]
       :from   [:foo]
       :where  [:= :foo.a "baz"]}
      (sql/format)
      (jdbc/execute! conn)))
