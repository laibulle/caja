(ns user
  (:require [juxt.clip.core :as clip]
            [aero.core :refer [read-config]]
            [postgres-db.interface :as di]
            [clojure.java.io :as io]
            [clojure.tools.namespace.repl :refer [refresh]]))

(def system-config (->
                    "config.edn"
                    (io/resource)
                    (read-config)
                    (:system-config)))
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
  (start)
  (reset))