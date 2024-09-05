(ns petitplat.server.migrate
  (:require
   [ragtime.next-jdbc :as jdbc]
   [ragtime.repl :as repl]
   [clojure.java.io :as io]))

(defn copy-file [source target]
  (with-open [in (io/input-stream source)
              out (io/output-stream target)]
    (io/copy in out)))

(defn merge-directories [source-dirs target-dir]
  (doseq [dir source-dirs
          file (file-seq (io/file dir))
          :when (.isFile file)]
    (let [relative-path (.relativize (.toPath (io/file dir)) (.toPath file))
          target-file (io/file target-dir (str relative-path))]
      (io/make-parents target-file)
      (copy-file file target-file))))

(defn gather-migrations []
  (merge-directories ["../../components/account/resources/migrations",
                      "../../components/food/resources/migrations"] "./resources/migrations"))

(def config
  {:datastore  (jdbc/sql-database {:connection-uri "jdbc:postgresql://localhost:5437/petitplat_dev?user=postgres&password=postgres"})
   :migrations (jdbc/load-directory "resources/migrations")})

(defn -main
  [& _]
  (gather-migrations))

(comment
  (gather-migrations)
  (repl/migrate config)
  (repl/rollback config))