(ns user
  (:require
   [postgres-db.interface :as pi]
   [messages.interface :as mi]
   [account.use-cases.send-reset-password-link-use-case :as splu]
   [account.use-cases.authenticate-use-case :as au]
   [account.use-cases.register-user-in-db-use-case :as ru]
   [account.use-cases.confirm-user-in-db-use-case :as cu]
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

(init)

(comment
  (init)

  (repl/migrate config)
  (repl/rollback config)

  (splu/execute {:email "jsssa@gmail.com"})

  (let [password "Noirfnefwerf#mopgmtrogmroptgm"
        user (ru/execute {:name "John Doe" :email "jsssa@gmail.com" :password password})]
    (cu/execute {:token (:confirmation-token user) :email (:email user)})
    (au/execute {:email (:email user) :password password})))