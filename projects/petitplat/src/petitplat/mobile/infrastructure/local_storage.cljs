(ns petitplat.mobile.infrastructure.local-storage
  (:require
   ["@react-native-async-storage/async-storage" :as ascs]))

(defn set-item [key value]
  (.setItem ascs key (clj->js value)))

(defn get-item [key]
  (-> (.getItem ascs key)
      (.then #(if (some? %)
                (js->clj % :keywordize-keys true)
                nil))))


(comment
  (.getItem ascs key)
  (set-item "myk" {:key "my key"})
  (get-item "myk"))