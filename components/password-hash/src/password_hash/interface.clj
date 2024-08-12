(ns password-hash.interface
  (:require [crypto.password.bcrypt :as password]))

(defn encrypt [password]
  (password/encrypt password))

(defn check [password encrypted]
  (password/check password encrypted))

(comment
  (let [password "hello"
        hash (encrypt password)]
    (check password hash)
    (check "wrong" hash)))