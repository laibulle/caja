(ns petitplat.mobile.infrastructure.local-storage
  (:require
   ["@react-native-async-storage/async-storage" :default async-storage]))

(defn set-item [key value]
  (.setItem async-storage key (.stringify js/JSON (clj->js value))))

(defn get-item [key]
  (-> ^js async-storage
      (.getItem  key)
      (.then #(if (some? %)
                (->
                 (.parse js/JSON %)
                 (js->clj :keywordize-keys true))
                nil))))


(comment
  (set-item "myk" {:key "my key"})
  (.then (get-item "myk")
         (fn [res]
           (js/console.log  (clj->js res)))))