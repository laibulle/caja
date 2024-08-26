(ns petitplat.mobile.core
  (:require
   [shadow.expo :as expo]
   ["react-native" :as rn]
   [helix.core :refer [defnc $]]
   ["@react-navigation/native" :refer [NavigationContainer]]
   ["@react-navigation/stack" :refer [createStackNavigator]]
   [helix.experimental.refresh :as refresh]))

(def stack (createStackNavigator))

(defnc details-screen []
  ($ rn/View
     ($ rn/Text "Details Screen")))

(defnc root [props]
  {:helix/features {:fast-refresh true}}
  ($ rn/View {:style #js {:flex 1, :alignItems "center", :justifyContent "center"}}
     ($ rn/Text {:style #js {:fontSize 36}}
        "Hello Helix!")))

(defnc stack-navigator []
  (let [stack-navigator (.-Navigator stack)
        stack-screen (.-Screen stack)]
    ($ NavigationContainer
       ($ stack-navigator
          ;; ($ StackScreen {:name "login"}
          ;;    (fn [props]
          ;;      ($ LoginScreen props)))
          ;; ($ stack-screen {:name "home"}
          ;;    (fn [props]
          ;;      ($ HomeScreen props)))
          ($ stack-screen {:name "details"}
             (fn [props]
               ($ details-screen props)))))))

(defn ^:dev/after-load after-load []
  (refresh/refresh!))

(defn start []
  (expo/render-root ($ stack-navigator))
  (refresh/inject-hook!))

;; (ns petitplat.mobile.core
;;   (:require
;;    ["expo" :as ex]
;;    [helix.core :refer [defnc $]]
;;    [helix.experimental.refresh :as refresh]
;;    [petitplat.mobile.events]
;;    [petitplat.mobile.subs]
;;    [re-frame.core :as rf]
;;    ["react-native" :as rn]
;;    ["@react-navigation/native" :refer [NavigationContainer]]
;;    ["@react-navigation/stack" :refer [createStackNavigator]]
;;    ;[petitplat.mobile.screens.login-screen :refer [LoginScreen]]
;;    [shadow.expo :as expo]
;;    [account.interface :refer [hello]]))

;; (def stack (createStackNavigator))

;; ;; Define your screen components using Helix's `defnc`
;; (defnc HomeScreen [{:keys [navigation]}]
;;   ($ rn/View
;;      ($ rn/Text "Home Screen")
;;      ($ rn/Button {:title "Go to details"
;;                    :on-press #(-> navigation (.navigate "details"))})))

;; (defnc DetailsScreen []
;;   ($ rn/View
;;      ($ rn/Text "Details Screen")))

;; (defnc StackNavigator []
;;   (let [StackNavigator (.-Navigator stack)
;;         StackScreen (.-Screen stack)]
;;     ($ NavigationContainer
;;        ($ StackNavigator
;;           ;; ($ StackScreen {:name "login"}
;;           ;;    (fn [props]
;;           ;;      ($ LoginScreen props)))
;;           ($ StackScreen {:name "home"}
;;              (fn [props]
;;                ($ HomeScreen props)))
;;           ($ StackScreen {:name "details"}
;;              (fn [props]
;;                ($ DetailsScreen props)))))))

;; (defnc Root [props]
;;   {:helix/features {:fast-refresh true}}
;;   ($ rn/View {:style #js {:flex 1, :alignItems "center", :justifyContent "center"}}
;;      ($ rn/Text {:style #js {:fontSize 36}}
;;         "Hello Helix!")))
;; (defnc mount-ui []
;;   (expo/render-root  Root))

;; (defn start
;;   {:dev/after-load true}
;;   []
;;   ;(rf/dispatch-sync [:initialize])
;;   (mount-ui))

;; (defn ^:dev/after-load after-load []
;;   (refresh/refresh!))

;; ;; (defn ^:dev/after-load clear-cache-and-render! []
;; ;;   ;; The `:dev/after-load` metadata causes this function to be called
;; ;;   ;; after shadow-cljs hot-reloads code. We force a UI update by clearing
;; ;;   ;; the Reframe subscription cache.
;; ;;   (refresh/refresh!)
;; ;;   (rf/clear-subscription-cache!)
;; ;;   (mount-ui))


;; (comment
;;   (start))

