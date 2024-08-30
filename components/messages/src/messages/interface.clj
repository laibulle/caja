(ns messages.interface
  (:require
   [messages.email.interface :as mi]
   [messages.email.templates.default-text :as dt]
   [messages.email.templates.default-html :as dh]))

(defn generate-email [input]
  {:text (dt/generate input)
   :html (dh/generate input)})


(defn send-email [input]
  (mi/send-message input))

(comment
  (send-email {:from "me@draines.com"
               :to "foo@example.com"
               :subject "Hi!"
               :body [:alternative
                      {:type "text/plain"
                       :content "This is a test."}

                      {:type "text/html"
                       :content "<b>Test!</b>"}]})

  (generate-email {:title "hello" :intro ["intro"] :outro ["outro"] :product {:name "My product" :link "http://link.com"}}))