(ns authentication.interface
  (:require [authentication.use-cases.authenticate-use-case :as authenticate-use-case]))

(defn authenticate [input]
  (authenticate-use-case/execute input))
