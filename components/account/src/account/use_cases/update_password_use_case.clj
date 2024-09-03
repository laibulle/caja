(ns account.use-cases.update-password-use-case
  (:require
   [common.interface :refer [=> collect-result]]
   [messages.interface :as mi]
   [taoensso.tower :as tower :refer (with-tscope)]
   [next.jdbc :as jdbc]
   [postgres-db.interface :as db]))


(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:input input :tx tx}
        collect-result)))

(comment
  (execute {:email "fnreifnre@gmail.com"}))