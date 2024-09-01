(ns postgres-db.interface
  (:require  [next.jdbc :as jdbc]))

;; Define the connection pool using c3p0
(defn create-datasource []
  (doto (com.mchange.v2.c3p0.ComboPooledDataSource.)
    (.setDriverClass "org.postgresql.Driver")
    (.setJdbcUrl "jdbc:postgresql://localhost:5437/petitplat_dev") ;; Update with your actual database URL
    (.setUser "postgres")                               ;; Update with your actual database username
    (.setPassword "postgres")                           ;; Update with your actual database password
    (.setMinPoolSize 5)
    (.setMaxPoolSize 20)))

(def datasource (atom nil))

(defn fetch-data []
  (jdbc/execute! @datasource ["SELECT * FROM your_table LIMIT 10"]))

(defn init []
  (reset! datasource (create-datasource)))

(comment
  (init)
  (fetch-data)
  ;(init {:dbtype "postgres" :dbname "thedb" :username "dbuser" :password "secret"
   ;      :dataSourceProperties {:socketTimeout 30}})
  )