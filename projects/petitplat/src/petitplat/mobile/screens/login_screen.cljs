(ns petitplat.mobile.screens.login-screen
  (:require
   [helix.core :refer [defnc $]]
   ;[re-frame.alpha :refer [subscribe dispatch sub]]
   ["expo-apple-authentication" :as AppleAuthentication]
   ["react-native" :refer [View Text Button StyleSheet]]))

(def styles
  (.create StyleSheet
           #js {:container #js {:flex 1
                                :alignItems "center"
                                :justifyContent "center"}
                :button #js {:width 200
                             :height 44}}))

(defnc login-screen [^js props]
  (let [;color @(subscribe [:color])
        handle-press
        (fn []
          (try
            (.then (AppleAuthentication/signInAsync
                    #js {:requestedScopes #js [(.-FULL_NAME AppleAuthentication/AppleAuthenticationScope)
                                               (.-EMAIL AppleAuthentication/AppleAuthenticationScope)]})
                   (fn [credential]
                     ;(dispatch [:sign-in {:provider :apple :credentials credential}])
                     (js/console.log "Signed in with credential" credential)))
            (catch js/Error e
              (if (= (.-code e) "ERR_REQUEST_CANCELED")
                               ;; handle that the user canceled the sign-in flow
                (js/console.log "User canceled sign-in")
                               ;; handle other errors
                (js/console.log "Sign-in error" e)))))]
    ($ View
       ($ Text "Login")
       ;($  Text color)
       ($ AppleAuthentication/AppleAuthenticationButton
          {:buttonType (.-SIGN_IN AppleAuthentication/AppleAuthenticationButtonType)
           :buttonStyle (.-BLACK AppleAuthentication/AppleAuthenticationButtonStyle)
           :cornerRadius 5
           :style (.-button styles)
           :onPress handle-press})
       ($ Button {:title "Sign In" :on-press (fn [])}))))
