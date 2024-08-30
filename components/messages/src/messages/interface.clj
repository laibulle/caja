(ns messages.interface
  (:require
   [messages.email.interface :as mi]
   [messages.email.templates.default-text :as dt]
   [messages.email.templates.default-html :as dh]))

(defn generate-email [input]
  {:text (dt/generate input)
   :html (dh/generate input)})


(defn send-email [input]
  (let [text (dt/generate (:variables input))
        email (dh/generate (:variables input))
        sm-input (assoc input :body [:alternative
                                     {:type "text/plain"
                                      :content text}
                                     {:type "text/html"
                                      :content email}])]

    (mi/send-message sm-input)))

(comment
  (send-email {:from "me@draines.com"
               :to "foo@example.com"
               :subject "Hi!"
               :variables {:title "hello" :intro ["intro"] :outro ["outro"] :product {:name "My product" :link "http://link.com"}}})

  (generate-email {:title "hello" :intro ["intro"] :outro ["outro"] :product {:name "My product" :link "http://link.com"}}))