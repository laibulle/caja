(ns app.petit-plat.authentication.infrastructure.jwt
  (:require
   [buddy.sign.jwt :as jwt]
   [clj-time.core :as time]))

(def jwt-secret "gZInYYYf3lFsAZLbMXkSi0rUaXIeEc1S")

(defn- account-expiration-date []
  (time/plus (time/now) (time/days 30)))

(defn generate-account-token [data]
  (-> data
      (assoc :exp (account-expiration-date))
      (jwt/sign jwt-secret)))

(defn unsign-account-token [token]
  (jwt/unsign token jwt-secret))


(comment
  (let [token (generate-account-token {:account-id 423})]
    (unsign-account-token token)))