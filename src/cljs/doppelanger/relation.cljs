(ns doppelanger.relation
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [reagent-material-ui.core.grid :refer [grid]]
            [reagent-material-ui.core.menu-item :refer [menu-item]]
            [reagent-material-ui.core.text-field :refer [text-field]]
            [reagent-material-ui.lab.autocomplete :refer [autocomplete]]
            [reagent-material-ui.core.textarea-autosize :refer [textarea-autosize]]
            [reagent-material-ui.core.typography :refer [typography]]
            [reagent-material-ui.core.paper :refer [paper]]
            [reagent-material-ui.core.button :refer [button]]
            [reagent-material-ui.core.chip :refer [chip]]
            [reagent-material-ui.styles :as style]
            [cljs.core.async :as async]
            ["@material-ui/core" :as mui]
            [ajax.core :as ajax]
            [clojure.string :as string]))

(defn event-value
  [^js/Event e]
  (.. e -target -value))

(defonce relations (r/atom []))

(defn load-relations! []
  (ajax/GET "/api/relation"
            {:handler (fn [response] (reset! relations response))}))
(load-relations!)

(defn data->chips [relation]
  (map #(vec [chip {:label (str (:domain %) "-" (:keyword %))}]) relation))

(defn- relations->chips-group [relations]
  (map #(vec [grid {:container "true" :id (key %)} [paper (data->chips (val %))]]) relations))

(defn relations->chips-group! []
  (let [relation @relations]
    (if relation
      (relations->chips-group @relations)
      [])))
(defn search-bar []
  [grid {:container "true" :justify "center" :alignItems "center"}
   (relations->chips-group!)])

(defn relation-page []
  [:section.section>div.container>div.content
   [:<>
    (search-bar)]])

