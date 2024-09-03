(ns account.use-cases.send-reset-password-link
  (:require
   [common.interface :refer [=> collect-result ErrorSchema]]
   [next.jdbc :as jdbc]
   [postgres-db.interface :as db]))



(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:input input}
        collect-result)))

(comment
  (execute {:email "fnreifnre@gmail.com"}))