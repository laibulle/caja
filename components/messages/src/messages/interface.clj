(ns messages.interface
  (:require
   [messages.email.interface :as mi]
   [messages.email.templates.default-text :as dt]
   [messages.email.templates.default-html :as dh]))

(defn init [input]
  (mi/init {:email input}))

(defn send-email [input]
  (let [text (dt/generate (:variables input))
        html (dh/generate (:variables input))
        sm-input (-> input
                     (assoc  :body [:alternative
                                    {:type "text/plain"
                                     :content text}
                                    {:type "text/html"
                                     :content html}])
                     (dissoc :variables))]

    (println html)

    (mi/send-message sm-input)))

(comment
  (init {:email {:host "localhost"
                 :port 8025}})
  (send-email {:from "me@draines.com"
               :to "foo@example.com"
               :subject "Hi!"
               :variables {:title "hello" :intro ["intro"] :outro ["outro"] :product {:name "My product" :link "http://link.com"}}}))