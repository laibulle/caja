(ns petitplat.mobile.infrastructure.local-storage-test
  (:require
   [cljs.test :refer-macros [deftest is async run-tests]]
   [petitplat.mobile.infrastructure.local-storage :as ls]
   ["@react-native-async-storage/async-storage" :as mock-async-storage]
   [reagent.core :as r]))

(defn mock-set-item [key value]
  (reset! mock-async-storage {key value}))

(defn mock-get-item [key]
  (get @mock-async-storage key))

(deftest test-set-item
  (async done
    ;; Create a mock async-storage object
         (with-redefs [ls/async-storage (r/atom {})]
      ;; Mock setItem function
           (with-redefs [mock-async-storage/setItem (fn [key value] (mock-set-item key value))]
        ;; Test
             (let [key "test-key"
                   value {:foo "bar"}]
               (ls/set-item key value)
               (is (= (get @mock-async-storage key) (.stringify js/JSON (clj->js value))))
               (done))))))

(deftest test-get-item
  (async done
    ;; Create a mock async-storage object
         (with-redefs [ls/async-storage (r/atom {})]
      ;; Mock getItem function
           (with-redefs [mock-async-storage/getItem (fn [key] (mock-get-item key))]
        ;; Test
             (let [key "test-key"
                   value {:foo "bar"}]
          ;; Set an item manually in the mock storage
               (reset! mock-async-storage {key (.stringify js/JSON (clj->js value))})
          ;; Test get-item
               (-> (ls/get-item key)
                   (.then (fn [result]
                            (is (= result value))
                            (done)))))))))

(comment
  (run-tests))