(ns petitplat.mobile.core
  (:require
   ["expo" :as ex]
   [re-frame.core :as rf]
   ["react-native" :as rn]
   ["@react-navigation/native" :refer [NavigationContainer]]
   ["@react-navigation/stack" :refer [createStackNavigator]]
   [petitplat.mobile.screens.login-screen :refer [LoginScreen]]
   [shadow.expo :as expo]
   [reagent.core :as r]
   [account.interface :refer [hello]]))

(def stack (createStackNavigator))

;; Define your screen components
(defn HomeScreen [^js props]
  [:> rn/View
   [:> rn/Text "Home Screen"]
   [:> rn/Button {:title "Go to details" :on-press (fn []
                                                     (.navigate (.-navigation props) "details"))}]])

(defn DetailsScreen [^js props]
  [:> rn/View
   [:> rn/Text "Details Screen"]])

(defn StackNavigator []
  (let [StackNavigator (.-Navigator stack)
        StackScreen (.-Screen stack)]
    [:> NavigationContainer
     [:> StackNavigator
      [:> StackScreen {:name "login"}
       (fn [^js props]
         (r/as-element [LoginScreen props]))]
      [:> StackScreen {:name "home"}
       (fn [^js props]
         (r/as-element [HomeScreen props]))]
      [:> StackScreen {:name "details"}
       (fn [^js props]
         (r/as-element [DetailsScreen props]))]]]))

(defn mount-ui []
  (expo/render-root (r/as-element [StackNavigator])))

(defn start
  []
  (rf/dispatch-sync [:initialize])
  (mount-ui))


(defn ^:dev/after-load clear-cache-and-render!
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code. We force a UI update by clearing
  ;; the Reframe subscription cache.
  (rf/clear-subscription-cache!)
  (mount-ui))


(comment
  (start))
