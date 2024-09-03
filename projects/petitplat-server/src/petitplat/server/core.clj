(ns petitplat.server.core
  (:require
   [juxt.clip.core :as clip]
   [clojure.java.io :as io]
   [aero.core :refer [read-config]]))

(defn -main
  [& _]
  (clip/start (:system-config (read-config (io/resource "config.edn"))))
  @(promise))