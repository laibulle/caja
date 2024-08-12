(ns  food.domain.food
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [malli.clj-kondo :as mc]
            [clojure.instant :as inst]))

(def FoodId [:int {:min 1}])
(def FoodCode [:string])
(def FoodName [:string])
(def FoodBrands [:string])
(def FoodProvider [:enum :off :off-csv-import])
(def FoodDescription [:string {:optional true}])
(def FoodMemberId [:int {:min 1 :optional true}])
(def FoodEnergy100 [:int {:min 0 :optional true}])
(def FoodMacronutriment100 [:float {:min 0 :optional true}])
(def ServingSize [:int {:min 0 :max 1000 :optional true}])
(def CoockedRatio [:float {:min 0 :max 1 :optional true}])
(def NutritionStatus [:enum :macro-complete])
(def DateTime [inst? {:optional false}])
(def DateTime? [inst? {:optional true}])

(def Food
  [:map
   [:id FoodId]
   [:name FoodName]
   [:code FoodCode]
   [:brands FoodBrands]
   [:provider FoodProvider]
   [:description FoodDescription]
   [:member-id FoodMemberId]
   [:energy-100 FoodEnergy100]
   [:fat-100 FoodMacronutriment100]
   [:carb-100 FoodMacronutriment100]
   [:sugar-100 FoodMacronutriment100]
   [:protein-100 FoodMacronutriment100]
   [:serving-size ServingSize]
   [:coocked-ratio CoockedRatio]
   [:nutrition-status [:set NutritionStatus]]
   [:deleted-at DateTime?]
   [:created-at DateTime]
   [:updated-at DateTime?]])

(comment
  (mg/generate Food))

;;     has_many :product_links, ProductLink
;;     has_many :ingredients, RecipeIngredient, on_replace: :delete
;;     has_many :steps, RecipeStep
