(ns user
  (:require [juxt.clip.core :as clip]
            [aero.core :refer [read-config]]
            [postgres-db.interface :as di]
            [clojure.tools.namespace.repl :refer [refresh]]))

(def system-config (:system-config (read-config (clojure.java.io/resource "config.edn"))))
(def system nil)

(defn start []
  (let [sys (clip/start system-config)]
    (println "System after start:" sys)
    (alter-var-root #'system (constantly sys))))

(defn stop []
  (alter-var-root #'system
                  (fn [s] (when s (clip/stop system-config s)))))

(defn reset []
  (stop)
  (refresh :after 'user/start))

(comment
  (println system-config)
  (start)
  (println @di/datasource)

  (reset))