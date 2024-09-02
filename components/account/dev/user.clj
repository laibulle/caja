(ns user
  (:require
   [postgres-db.interface :as pi]
   [account.use-cases.register-user-in-db-use-case :as ru]
   [ragtime.next-jdbc :as jdbc]
   [ragtime.repl :as repl]))

(defn init []
  (pi/init {:url "jdbc:postgresql://localhost:5437/petitplat_dev?user=postgres&password=postgres"}))

(def config
  {:datastore  (jdbc/sql-database {:connection-uri "jdbc:postgresql://localhost:5437/petitplat_dev?user=postgres&password=postgres"})
   :migrations (jdbc/load-resources "migrations")})

(comment
  (init)

  (repl/migrate config)
  (repl/rollback config)


  (ru/execute {:name "John Doe" :email "j@gmail.com" :password "Noirfnefwerf#mopgmtrogmroptgm"}))