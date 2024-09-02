(ns user
  (:require
   [postgres-db.interface :as pi]
   [messages.interface :as mi]
   [account.use-cases.register-user-in-db-use-case :as ru]
   [ragtime.next-jdbc :as jdbc]
   [ragtime.repl :as repl]))

(defn init []
  (pi/init {:url "jdbc:postgresql://localhost:5437/petitplat_dev?user=postgres&password=postgres"})
  (mi/init {:email {:host "localhost"
                    :port 8025
                    :default-from {:email "me@draines.com" :name "Me"}
                    :base-url "http://base-url"
                    :product {:name "My product" :link "https://www.google.fr"}}}))

(def config
  {:datastore  (jdbc/sql-database {:connection-uri "jdbc:postgresql://localhost:5437/petitplat_dev?user=postgres&password=postgres"})
   :migrations (jdbc/load-resources "migrations")})

(comment
  (init)

  (repl/migrate config)
  (repl/rollback config)


  (ru/execute {:name "John Doe" :email "js@gmail.com" :password "Noirfnefwerf#mopgmtrogmroptgm"}))