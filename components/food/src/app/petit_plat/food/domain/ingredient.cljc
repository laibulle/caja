(ns app.petit-plat.food.domain.ingredient
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [malli.clj-kondo :as mc]
            [clojure.instant :as inst]))

(def IngredientId [:int {:min 1}])
(def FoodId [:int {:min 1}])
(def Quantity [:float {:min 0}])

(def Ingredient [:map
                 [:id IngredientId]
                 [:quantity Quantity]
                 [:ingredient-food-id FoodId]
                 [:food-id FoodId]])

(comment
  (mg/generate Ingredient))
