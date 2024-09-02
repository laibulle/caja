(ns messages.interface
  (:require
   [messages.email.interface :as mi]))

(defn init [input]
  (mi/init (:email input)))

(defn send-email-from-template [input]
  (mi/send-email-from-template input))

(defn create-email-link [path]
  (str (get-in @mi/config [:base-url]) path))

(comment
  (init {:email {:host "localhost"
                 :port 8025
                 :default-from {:email "me@draines.com" :name "Me"}
                 :base-url "http://base-url"
                 :product {:name "My product" :link "https://www.google.fr"}}})

  (create-email-link "/fre")
  (send-email-from-template {:to "foo@example.com"
                             :subject "Hi!"
                             :variables {:title "hello" :intro ["intro"] :outro ["outro"] :product {:name "My product" :link "http://link.com"}}}))