(ns app.petit-plat.datomic-db.interface
  (:require
   [datomic.client.api :as d]))

(def db-name "petit-plat-dev")

(def client (d/client {:server-type :datomic-local
                       :system "petit-plat-dev"}))

(def conn (atom nil))

(defn connect []
  (reset! conn (d/connect client {:db-name db-name})))

(defn create-schema [schema]
  (d/transact @conn {:tx-data schema}))

(defn add-namespace [ns kw]
  (keyword ns (name kw)))

(defn remove-namespace [kw]
  (keyword (name kw)))

(defn domain-to-db [ns m]
  (into {}
        (for [[k v] m]
          [(add-namespace ns k) v])))

(defn db-to-domain [input]
  (into {}
        (for [[k v] input]
          [(remove-namespace k) v])))

(defn insert-one [input]
  (let [result (d/transact @conn {:tx-data [input]})]
    input))

(defn update [input]
  (let [result (d/transact @conn {:tx-data [input]})]
    input))

(defn create-database []
  (d/create-database client {:db-name db-name}))

(defn delete-database []
  (d/delete-database client {:db-name db-name}))

(defn list-in-db [query & params]
  (apply d/q query (d/db @conn) params))

(defn find-one [query & params]
  (let [results (apply d/q query (d/db @conn) params)]
    (if (> (count results) 1)
      (throw (ex-info "Multiple entries found" {}))
      (if (empty? results)
        nil
        (first results)))))

(comment

  (create-database))

