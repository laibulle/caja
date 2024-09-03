(ns petitplat.server.core
  (:require
   [juxt.clip.core :as clip]
   [aero.core :refer [read-config]]))

(defn -main
  [& _]
  (clip/start (:system-config (read-config (clojure.java.io/resource "config.edn"))))
  @(promise))