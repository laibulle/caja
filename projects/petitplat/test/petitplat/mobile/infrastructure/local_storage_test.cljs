(ns petitplat.mobile.infrastructure.local-storage-test
  (:require
   [cljs.test :refer-macros [deftest is async run-tests]]
   [petitplat.mobile.infrastructure.local-storage :as ls]
   ["@react-native-async-storage/async-storage" :as mock-async-storage]))

(deftest test-set-item
  (async done
    ;; Mock setItem function
         (with-redefs [mock-async-storage/setItem (fn [key value]
                                                    (reset! (atom {}) {key value}))]
      ;; Test
           (let [key "test-key"
                 value {:foo "bar"}]
             (ls/set-item key value)
             (is (= (get @mock-async-storage key) (.stringify js/JSON (clj->js value))))
             (done)))))

(deftest test-get-item
  (async done
    ;; Mock getItem function
         (with-redefs [mock-async-storage/getItem (fn [key]
                                                    (js/Promise.resolve (.stringify js/JSON (clj->js {:foo "bar"}))))]
      ;; Test
           (let [key "test-key"]
             (-> (ls/get-item key)
                 (.then (fn [result]
                          (is (= result {:foo "bar"}))
                          (done))))))))

(comment
  (run-tests))
