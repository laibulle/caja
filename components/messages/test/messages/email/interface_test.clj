(ns messages.email.interface-test
  (:require [clojure.test :refer :all]
            [messages.email.interface :refer :all]
            [postal.core :as p]
            [messages.email.templates.default-text :as dt]
            [messages.email.templates.default-html :as dh]))

;; Mock implementations for external dependencies
(defn mock-send-message [config message]
  (is (= (:host config) "localhost") "Config host should be localhost")
  (is (= (:port config) 8025) "Config port should be 8025")
  {:error :SUCCESS})

(defn mock-generate-text [variables]
  "Mocked text email content")

(defn mock-generate-html [variables]
  "<b>Mocked HTML email content</b>")

(deftest test-init
  (testing "Initialization of email configuration"
    (init {:host "localhost"
           :port 8025
           :default-from {:email "me@draines.com" :name "Me"}
           :product {:name "My product" :link "https://www.google.fr"}})
    (is (= @config {:host "localhost"
                    :port 8025
                    :default-from {:email "me@draines.com" :name "Me"}
                    :product {:name "My product" :link "https://www.google.fr"}})
        "The config atom should be set with the provided configuration")))

(deftest test-add-default-from
  (testing "Add default from to message"
    (init {:default-from {:email "me@draines.com" :name "Me"}})
    (let [message {:to "foo@example.com"}
          result (add-default-from message)]
      (is (= (:from result) "me@draines.com") "Default :from should be added to the message")
      (is (= (:to result) "foo@example.com") "Original :to field should be preserved")))

  (testing "No overwrite of existing :from"
    (let [message {:to "foo@example.com" :from "someone@example.com"}
          result (add-default-from message)]
      (is (= (:from result) "someone@example.com") "Existing :from field should not be overwritten"))))

(deftest test-send-email
  (testing "Successful email sending"
    (with-redefs [p/send-message mock-send-message]
      (init {:host "localhost" :port 8025})
      (let [message {:to "foo@example.com"}
            result (send-email message)]
        (is (true? result) "send-email should return true on success"))))

  (testing "Failed email sending"
    (with-redefs [p/send-message (fn [_ _] {:error :FAILURE})]
      (init {:host "localhost" :port 8025})
      (let [message {:to "foo@example.com"}
            result (send-email message)]
        (is (map? result) "send-email should return a map on failure")
        (is (= (:errors result) [{:error :FAILURE}]) "Result should contain the error details")))))

(deftest test-send-email-from-template
  (testing "Send email from template"
    (with-redefs [p/send-message mock-send-message
                  dt/generate mock-generate-text
                  dh/generate mock-generate-html]
      (init {:host "localhost"
             :port 8025
             :default-from {:email "me@draines.com" :name "Me"}
             :product {:name "My product" :link "https://www.google.fr"}})
      (let [input {:to "foo@example.com"
                   :subject "Hi!"
                   :variables {:title "hello"
                               :intro ["intro"]
                               :outro ["outro"]
                               :product {:name "My product" :link "http://link.com"}}}
            result (send-email-from-template input)]
        (is (true? result) "send-email-from-template should return true on success")))))

(run-tests)
