(ns email.templates.default-text)

(defn generate [{:keys [title intro dictionary outro action signature product]}]
  (str
   title "\n\n"

   (apply str (map #(str % "\n") intro)) "\n"

   (apply str (map #(str (clojure.string/capitalize (name (key %))) ": " (val %) "\n") dictionary)) "\n"

   (apply str (map
               (fn [action_item]
                 (str (:instructions action_item) "\n"
                      (apply str (map #(str (:link %) "\n") (:button action_item)))
                      "\n"))
               action)) "\n"

   (apply str (map #(str % "\n") outro)) "\n"

   (when signature
     (str signature ",\n" (:name product) "\n"))

   "\n\n© " (.getYear (java.time.LocalDate/now)) " " (:name product) ". All rights reserved."))


(comment
  (generate {:title "hello" :intro ["intro"] :outro ["outro"] :product {:name "My product" :link "http://link.com"}}))
