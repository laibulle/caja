(ns postgres-db.interface
  (:require  [next.jdbc :as jdbc]))

;; Define the connection pool using c3p0
(defn create-datasource [config]
  (doto (com.mchange.v2.c3p0.ComboPooledDataSource.)
    (.setDriverClass "org.postgresql.Driver")
    (.setJdbcUrl (:url config)) ;; Update with your actual database URL
    (.setUser (:user config))                               ;; Update with your actual database username
    (.setPassword (:password config))                           ;; Update with your actual database password
    (.setMinPoolSize 5)
    (.setMaxPoolSize 20)))

(def datasource (atom nil))

(defn execute [statement]
  (jdbc/execute! @datasource statement))

(defn fetch-data []
  (jdbc/execute! @datasource ["SELECT * FROM your_table LIMIT 10"]))

(defn init [config]
  (reset! datasource (create-datasource config)))

(comment
  (init {:url "jdbc:postgresql://localhost:5437/petitplat_dev" :user "postgres" :password "postgres"})
  (fetch-data))