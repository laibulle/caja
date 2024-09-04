(ns messages.interface-test
  (:require [clojure.test :refer :all]
            [messages.interface :refer :all]
            [messages.email.interface :as mi]
            [clojure.string :as str]))

;; Mock implementations for the external functions
(defn mock-init [email-config]
  (is (= (:host email-config) "localhost") "Email config host should be localhost")
  (is (= (:port email-config) 8025) "Email config port should be 8025"))

(defn mock-send-email-from-template [input]
  (is (string? (:to input)) "The :to field should be a string representing an email address")
  (is (string? (:subject input)) "The :subject field should be a string"))

(deftest test-init
  (testing "Initialization of email configuration"
    (with-redefs [mi/init mock-init]
      (init {:email {:host "localhost"
                     :port 8025
                     :default-from {:email "me@draines.com" :name "Me"}
                     :base-url "http://base-url"
                     :product {:name "My product" :link "https://www.google.fr"}}}))))

(deftest test-send-email-from-template
  (testing "Sending email from template"
    (with-redefs [mi/send-email-from-template mock-send-email-from-template]
      (send-email-from-template {:to "foo@example.com"
                                 :subject "Hi!"
                                 :variables {:title "hello"
                                             :intro ["intro"]
                                             :outro ["outro"]
                                             :product {:name "My product" :link "http://link.com"}}}))))

(deftest test-create-email-link
  (testing "Creation of email link"
    (with-redefs [mi/config (atom {:base-url "http://base-url"})]
      (let [result (create-email-link "/fre")]
        (is (str/starts-with? result "http://base-url") "Link should start with the base URL")
        (is (= result "http://base-url/fre") "Link should match the base URL concatenated with the path")))))

(run-tests)
