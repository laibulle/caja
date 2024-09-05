(ns food.domain.recipe
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [malli.clj-kondo :as mc]
            [clojure.instant :as inst]))

(def RecipeId [:int {:min 1}])
(def RecipeName [:string])
(def MemberId [:int {:min 1}])

(def DateTime [inst? {:optional false}])
(def DateTime? [inst? {:optional true}])

(def Recipe
  [:map
   [:id RecipeId]
   [:member-id MemberId]
   [:name RecipeName]
   [:deleted-at DateTime?]
   [:created-at DateTime]
   [:updated-at DateTime?]])

(comment
  (mg/generate Recipe))
