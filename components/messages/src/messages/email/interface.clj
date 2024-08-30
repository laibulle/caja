(ns messages.email.interface
  (:require [postal.core :as p]))

(def conn {:host "localhost"
           :port 8025})

(def default-from "me@draines.com")

(defn add-default-from [message]
  (if (nil? (:from message))
    (assoc message :from default-from)
    message))

(defn send-message [message]
  (println message)
  (let [new-message (add-default-from message)
        result (p/send-message conn new-message)]
    (if (= (:error result) :SUCCESS)
      true
      {:errors [result]})))


(comment
  (send-message {:from "me@draines.com"
                 :to "foo@example.com"
                 :subject "Hi!"
                 :body [:alternative
                        {:type "text/plain"
                         :content "This is a test."}

                        {:type "text/html"
                         :content "<b>Test!</b>"}]}))