(ns petitplat.server.migrate
  (:require
   [ragtime.next-jdbc :as jdbc]
   [ragtime.repl :as repl]
   [clojure.java.io :as io])
  (:import [java.nio.file Paths Files]))

(defn copy-file [source target]
  (io/copy (io/file source) (io/file target)))

(defn merge-directories [source-dirs target-dir]
  (doseq [dir source-dirs
          file (file-seq (io/file dir))
          :when (.isFile file)]
    (let [source-path (Paths/get (.getAbsolutePath (io/file dir)) (into-array String []))
          file-path (Paths/get (.getAbsolutePath file) (into-array String []))
          relative-path (.relativize source-path file-path)
          target-file (io/file target-dir (.toString relative-path))]
      (io/make-parents target-file)
      (copy-file file target-file))))

(defn gather-migrations []
  (merge-directories ["../../components/account/resources/migrations"] "resources/migrations"))

(def config
  {:datastore  (jdbc/sql-database {:connection-uri "jdbc:postgresql://localhost:5437/petitplat_dev?user=postgres&password=postgres"})
   :migrations (jdbc/load-resources "migrations")})

(comment
  (gather-migrations)
  (repl/migrate config)
  (repl/rollback config))