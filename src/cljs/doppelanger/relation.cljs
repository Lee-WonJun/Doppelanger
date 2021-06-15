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


(defn search-bar []
  [grid {:container "true" :justify "center" :alignItems "center"}
   [chip {:label "asdf/asfd"}]
  ])

(defn relation-page []
  [:section.section>div.container>div.content
   [:<>
    (search-bar)]])

