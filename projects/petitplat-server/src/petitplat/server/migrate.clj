(ns petitplat.server.migrate
  (:require
   [ragtime.next-jdbc :as jdbc]
   [ragtime.repl :as repl]))


(def config
  {:datastore  (jdbc/sql-database {:connection-uri "jdbc:postgresql://localhost:5437/petitplat_dev?user=postgres&password=postgres"})
   :migrations (jdbc/load-resources "migrations")})

(comment
  (repl/migrate config)
  (repl/rollback config))