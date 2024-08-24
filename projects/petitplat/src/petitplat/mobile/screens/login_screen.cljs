(ns petitplat.mobile.screens.login-screen
  (:require
   ["expo-apple-authentication" :as AppleAuthentication]
   ["react-native" :refer [View Text Button StyleSheet]]))

(def styles
  (.create StyleSheet
           #js {:container #js {:flex 1
                                :alignItems "center"
                                :justifyContent "center"}
                :button #js {:width 200
                             :height 44}}))

(defn LoginScreen [^js props]
  (let [handle-press (fn []
                       (try
                         (.then (AppleAuthentication/signInAsync
                                 #js {:requestedScopes #js [(.-FULL_NAME AppleAuthentication/AppleAuthenticationScope)
                                                            (.-EMAIL AppleAuthentication/AppleAuthenticationScope)]})
                                (fn [credential]
                                   ;; signed in
                                  (js/console.log "Signed in with credential" credential)))
                         (catch js/Error e
                           (if (= (.-code e) "ERR_REQUEST_CANCELED")
                               ;; handle that the user canceled the sign-in flow
                             (js/console.log "User canceled sign-in")
                               ;; handle other errors
                             (js/console.log "Sign-in error" e)))))]
    [:> View
     [:> Text "Login"]
     [:> AppleAuthentication/AppleAuthenticationButton
      {:buttonType (.-SIGN_IN AppleAuthentication/AppleAuthenticationButtonType)
       :buttonStyle (.-BLACK AppleAuthentication/AppleAuthenticationButtonStyle)
       :cornerRadius 5
       :style (.-button styles)
       :onPress handle-press}]
   ;[:> AppleAuthentication/AppleAuthentication]
     [:> Button {:title "Sign In" :on-press (fn [])}]]))
