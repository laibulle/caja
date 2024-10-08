(ns messages.email.interface
  (:require
   [messages.email.templates.default-text :as dt]
   [messages.email.templates.default-html :as dh]
   [postal.core :as p]))

(def config (atom nil))

(defn init [config-input]
  (reset! config config-input))

(defn add-default-from [message]
  (if (nil? (:from message))
    (assoc message :from (get-in @config [:default-from :email]))
    message))

(defn send-email [message]
  (let [new-message (add-default-from message)
        result (p/send-message @config new-message)]
    (if (= (:error result) :SUCCESS)
      true
      {:errors [result]})))

(defn send-email-from-template [input]
  (let [variables (assoc (:variables input) :product (:product @config))
        text (dt/generate variables)
        html (dh/generate variables)
        sm-input (-> input
                     (assoc  :body [:alternative
                                    {:type "text/plain"
                                     :content text}
                                    {:type "text/html"
                                     :content html}])
                     (dissoc :variables))]
    (send-email sm-input)))

(comment
  (init {:host "localhost"
         :port 8025
         :default-from {:email "me@draines.com" :name "Me"}
         :product {:name "My product" :link "https://www.google.fr"}})
  (add-default-from {})
  (send-email-from-template {:to "foo@example.com"
                             :subject "Hi!"
                             :body [:alternative
                                    {:type "text/plain"
                                     :content "This is a test."}

                                    {:type "text/html"
                                     :content "<b>Test!</b>"}]}))