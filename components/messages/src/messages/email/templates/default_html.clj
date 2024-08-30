(ns messages.email.templates.default-html
  (:require [hiccup.core :as hiccup]))

(defn generate [{:keys [title table intro dictionary outro action signature product go_to_action text-direction]}]
  (hiccup/html

   [:doctype :html]
   [:html {:xmlns "http://www.w3.org/1999/xhtml"}
    [:head
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     [:meta {:http-equiv "Content-Type" :content "text/html; charset=UTF-8"}]
     [:style {:type "text/css" :rel "stylesheet" :media "all"}
      "/* Base Styles */"
      "*:not(br):not(tr):not(html) { font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; box-sizing: border-box; }"
      "body { width: 100% !important; height: 100%; margin: 0; line-height: 1.4; background-color: #F2F4F6; color: #74787E; text-size-adjust: none; }"
      "a { color: #3869D4; }"
      "/* Layout Styles */"
      ".email-wrapper { width: 100%; margin: 0; padding: 0; background-color: #F2F4F6; }"
      ".email-content { width: 100%; margin: 0; padding: 0; }"
      ".email-masthead { padding: 25px 0; text-align: center; }"
      ".email-masthead_logo { max-width: 400px; border: 0; }"
      ".email-masthead_name { font-size: 16px; font-weight: bold; color: #2F3133; text-decoration: none; text-shadow: 0 1px 0 white; }"
      ".email-logo { max-height: 50px; }"
      "/* Body Styles */"
      ".email-body { width: 100%; margin: 0; padding: 0; border-top: 1px solid #EDEFF2; border-bottom: 1px solid #EDEFF2; background-color: #FFF; }"
      ".email-body_inner { width: 570px; margin: 0 auto; padding: 0; }"
      ".email-footer { width: 570px; margin: 0 auto; padding: 0; text-align: center; }"
      ".email-footer p { color: #AEAEAE; }"
      ".body-action { width: 100%; margin: 30px auto; padding: 0; text-align: center; }"
      ".body-dictionary { width: 100%; overflow: hidden; margin: 20px auto 10px; padding: 0; }"
      ".body-dictionary dd { margin: 0 0 10px 0; }"
      ".body-dictionary dt { clear: both; color: #000; font-weight: bold; }"
      ".body-sub { margin-top: 25px; padding-top: 25px; border-top: 1px solid #EDEFF2; }"
      ".content-cell { padding: 35px; }"
      ".align-right { text-align: right; }"
      "/* Typography */"
      "h1 { margin-top: 0; color: #2F3133; font-size: 19px; font-weight: bold; }"
      "h2 { margin-top: 0; color: #2F3133; font-size: 16px; font-weight: bold; }"
      "h3 { margin-top: 0; color: #2F3133; font-size: 14px; font-weight: bold; }"
      "p { margin-top: 0; color: #74787E; font-size: 16px; line-height: 1.5em; }"
      "p.sub { font-size: 12px; }"
      "p.center { text-align: center; }"
      "/* Data table */"
      ".data-table-title { width: 100%; margin: 0; font-size: 17px; padding: 20px 0 15px 0; }"
      ".data-wrapper { width: 100%; margin: 0; padding: 0 0 35px 0; }"
      ".data-table { width: 100%; margin: 0; }"
      ".data-table th { text-align: left; padding: 0px 5px; padding-bottom: 8px; border-bottom: 1px solid #EDEFF2; }"
      ".data-table th p { margin: 0; color: #9BA2AB; font-size: 12px; }"
      ".data-table td { padding: 10px 5px; color: #74787E; font-size: 15px; line-height: 18px; }"
      "/* Buttons */"
      ".button { display: inline-block; width: 200px; border-radius: 3px; color: #ffffff; font-size: 15px; line-height: 45px; text-align: center; text-decoration: none; text-size-adjust: none; mso-hide: all; }"
      "/* Media Queries */"
      "@media only screen and (max-width: 600px) { .email-body_inner, .email-footer { width: 100% !important; } }"
      "@media only screen and (max-width: 500px) { .button { width: 100% !important; } }"]]
    [:body {:dir text-direction}
     [:table.email-wrapper {:width "100%" :cellpadding 0 :cellspacing 0}
      [:tr
       [:td {:align "center"}
        [:table.email-content {:width "100%" :cellpadding 0 :cellspacing 0}
        ;; Logo Section
         [:tr
          [:td.email-masthead
           [:a.email-masthead_name {:href (:link product) :target "_blank"}
            (if (contains? product :logo)
              [:img {:src (:logo product) :style (str "height: " (:logo-height product) ";") :alt ""}]
              (:name product))]]]

        ;; Email Body Section
         [:tr
          [:td.email-body {:width "100%"}
           [:table.email-body_inner {:align "center" :width "570" :cellpadding 0 :cellspacing 0}
           ;; Body Content
            [:tr
             [:td.content-cell
              [:h1 title]

              (for [intro_item intro]
                [:p intro_item])

             ;; Dictionary
              [:dl.body-dictionary
               (for [[k v] dictionary]
                 [:<>
                  [:dt (clojure.string/capitalize (name k)) ":"]
                  [:dd v]])]

             ;; Table
              (for [[table_item i] (map vector table (range))]
                [:<>
                 [:h1.data-table-title (:title table_item)]
                 [:table.data-wrapper {:width "100%" :cellpadding 0 :cellspacing 0}
                  [:tr
                   [:td {:colspan 2}
                    [:table.data-table {:width "100%" :cellpadding 0 :cellspacing 0}
                     [:tr
                      (for [column (first (:data table_item))]
                        [:th [:p (clojure.string/capitalize (name (first column)))]])]
                     (for [row (:data table_item)]
                       [:tr
                        (for [column row]
                          [:td {:style (when-let [alignment (get-in table_item [:columns :custom_alignment (first column)])]
                                         (str "text-align:" alignment ";"))}
                           (second column)])])]]]]])

             ;; Action
              (for [action_item action]
                [:<>
                 [:p (:instructions action_item)]
                 [:table.body-action {:align "center" :cellpadding 0 :cellspacing 0}
                  [:tr
                   (for [action_button (:button action_item)]
                     [:td {:align "center"}
                      [:a.button {:href (:link action_button) :target "_blank" :style (str "background-color: " (:color action_button) ";")}
                       (:text action_button)]])]]])

             ;; Gmail Go-To Actions
              (when go_to_action
                [:script {:type "application/ld+json"}
                 (str "{
                   \"@context\": \"http://schema.org\",
                   \"@type\": \"EmailMessage\",
                   \"potentialAction\": {
                     \"@type\": \"ViewAction\",
                     \"url\": \"" (:link go_to_action) "\",
                     \"target\": \"" (:link go_to_action) "\",
                     \"name\": \"" (:text go_to_action) "\"
                   },
                   \"description\": \"" (:description go_to_action) "\"
                 }")])

              (for [outro_item outro]
                [:p outro_item])

              (when signature
                [:p
                 signature ","
                 [:br]
                 (:name product)])]]]]]

        ;; Footer Section
         [:tr
          [:td
           [:table.email-footer {:align "center" :width "570" :cellpadding 0 :cellspacing 0}
            [:tr
             [:td.content-cell
              [:p.sub.center
               "&copy; " (.getYear (java.time.LocalDate/now)) " "
               [:a {:href (:link product) :target "_blank"} (:name product)]
               ". All rights reserved."]]]]]]]]]]]]))

(comment
  (generate {:title "hello" :intro ["intro"] :outro ["outro"] :product {:name "My product" :link "http://link.com"}}))