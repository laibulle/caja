(ns petitplat.mobile.infrastructure.local-storage
  (:require
   ["@react-native-async-storage/async-storage" :as AsyncStorage]))

(defn set-item [key value]
  (.setItem AsyncStorage key (clj->js value)))

(defn get-item [key]
  (-> (.getItem AsyncStorage key)
      (.then #(if (some? %)
                (js->clj % :keywordize-keys true)
                nil))))


(comment
  (set-item "myk" {:key "my key"})
  (get-item "myk"))