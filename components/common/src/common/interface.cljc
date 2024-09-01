(ns common.interface)

(defn => [result next-fn]
  (if (some? (:errors result))
    result
    (next-fn result)))

(defn collect-result [{:keys [data errors]}]
  (if (some? errors)
    {:errors errors}
    data))

(def ErrorSchema
  [:map
   [:errors [:vector keyword?]]])