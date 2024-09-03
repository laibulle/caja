(ns user
  (:require [messages.interface :as mi]))

(comment
  (mi/init {:email {:host "localhost"
                    :port 8025
                    :default-from {:email "me@draines.com" :name "Me"}
                    :base-url "http://base-url"
                    :product {:name "My product" :link "https://www.google.fr"}}}))