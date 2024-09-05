(ns food.use-cases.create-recipe-use-case
  (:require
   [malli.core :as m]
   [food.domain.recipe :as d]
   [next.jdbc :as jdbc]
   [postgres-db.interface :as db]
   [common.interface :refer [=> collect-result]]
   [food.infrastructure.adapters.postgres.postgres-foods-adapter :as fa]))

(def invalid-input :invalid-input)

(def CreateRecipeInput
  [:map
   [:member-id d/MemberId]
   [:name d/RecipeName]])

(defn validate-create-recipe-input [input]
  (m/validate CreateRecipeInput input))

(defn- validate-input [input]
  (if (validate-create-recipe-input (:input input))
    input
    {:errors [invalid-input]}))

(defn- save-in-db [input]
  (fa/insert-food (:tx input) (:input input)))

(defn execute [input]
  (jdbc/with-transaction [tx @db/datasource]
    (-> {:input input :tx tx}
        (validate-input)
        (=> save-in-db))))

(comment
  (def input {:member-id 1 :name "Test recipe"})
  (validate-create-recipe-input input)
  (execute input))