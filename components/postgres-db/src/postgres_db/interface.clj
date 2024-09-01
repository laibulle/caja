(ns postgres-db.interface
  (:require  [next.jdbc :as jdbc]
             [next.jdbc.connection :as conn])
  (:import (com.mchange.v2.c3p0 ComboPooledDataSource PooledDataSource)))

(def db-spec
  {:jdbcUrl "jdbc:postgresql://localhost:5432/yourdb"  ;; Replace with your actual DB URL
   :user "yourusername"                               ;; Replace with your actual DB user
   :password "yourpassword"                           ;; Replace with your actual DB password
   ;; C3P0 specific settings
   :connectionPoolDataSource (doto (com.mchange.v2.c3p0.ComboPooledDataSource.)
                               (.setDriverClass "org.postgresql.Driver")
                               (.setJdbcUrl "jdbc:postgresql://localhost:5432/yourdb")
                               (.setUser "yourusername")
                               (.setPassword "yourpassword")
                               (.setMinPoolSize 5)
                               (.setMaxPoolSize 20))})

(def datasource (conn/->pool ComboPooledDataSource db-spec))

(defn get-connection []
  (jdbc/get-datasource db-spec))

(defn fetch-data []
  (jdbc/execute! (get-connection) ["SELECT * FROM your_table LIMIT 10"]))



(comment
  (fetch-data)
  ;(init {:dbtype "postgres" :dbname "thedb" :username "dbuser" :password "secret"
   ;      :dataSourceProperties {:socketTimeout 30}})
  )