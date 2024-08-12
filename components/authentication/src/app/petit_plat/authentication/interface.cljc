(ns app.petit-plat.authentication.interface
  (:require [app.petit-plat.authentication.use-cases.authenticate-use-case :as authenticate-use-case]))

(defn authenticate [input]
  (authenticate-use-case/execute input))
