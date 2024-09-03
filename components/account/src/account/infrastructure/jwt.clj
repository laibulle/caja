(ns account.infrastructure.jwt
  (:require
   [buddy.sign.jwt :as jwt])
  (:import [java.sql Timestamp]
           [java.util Calendar]))

(def jwt-secret "gZInYYYf3lFsAZLbMXkSi0rUaXIeEc1S")

(defn- account-expiration-date []
  (let [calendar (Calendar/getInstance)]
    (.add calendar Calendar/DAY_OF_MONTH 30)
    (Timestamp. (.getTimeInMillis calendar))))

(defn generate-account-token [data]
  (-> data
      (assoc :exp (.getTime (account-expiration-date)))  ; Store expiration as Unix timestamp (milliseconds)
      (jwt/sign jwt-secret)))

(defn unsign-account-token [token]
  (jwt/unsign token jwt-secret))

(comment
  (let [token (generate-account-token {:account-id 423})]
    (unsign-account-token token)))
