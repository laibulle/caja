(ns user
  (:require [juxt.clip.core :as clip]
            [aero.core :refer [read-config]]
            [clojure.tools.namespace.repl :refer [refresh]]))

(def system-config (read-config "resources/config.edn"))
(def system nil)

(defn start []
  (alter-var-root #'system (constantly (clip/start system-config))))

(defn stop []
  (alter-var-root #'system
                  (fn [s] (when s (clip/stop system-config s)))))

(defn reset []
  (stop)
  (refresh :after 'dev/go))

(comment
  (println system-config)
  (start))