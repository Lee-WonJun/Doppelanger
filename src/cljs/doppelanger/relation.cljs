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
            [cljs.core.async :as async]
            ["@material-ui/core" :as mui]
            [ajax.core :as ajax]
            [clojure.string :as string]))

(defn event-value
  [^js/Event e]
  (.. e -target -value))

(defonce domains (r/atom []))
(defonce search-domain (r/atom nil))
(defonce search-keyword (r/atom nil))

(defn load-domains! []
  (ajax/GET "/api/domain"
            {:handler (fn [response] (reset! domains response))}))
(load-domains!)

(defn keyword-field []
  [text-field
   {:value       @search-keyword
    :label       "label"
    :input-props {:style {:text-align "center"}}
    :style       {:width 500}
    :variant     "outlined"}
   ])

(defn autocomplete-domain []
  [autocomplete {:options         @domains
                 :style           {:width 250}
                 :on-input-change (fn [e v]
                                    (.log js/console v)
                                    (reset! search-domain v)
                                    )
                 :render-input    (fn [^js params]
                                    (set! (.-variant params) "outlined")
                                    (set! (.-label params) "domain")
                                    (r/create-element mui/TextField params))}])


(defn search-bar []
  [grid {:container "true" :justify "center" :alignItems "center"}
   (autocomplete-domain)
   (keyword-field)
   [button]
  ])

(defn relation-page []
  [:section.section>div.container>div.content
   [:<>
    (search-bar)]])

