(ns doppelanger.home
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [reagent-material-ui.core.grid :refer [grid]]
            [reagent-material-ui.core.menu-item :refer [menu-item]]
            [reagent-material-ui.core.text-field :refer [text-field]]
            [reagent-material-ui.lab.autocomplete :refer [autocomplete]]
            [reagent-material-ui.core.textarea-autosize :refer [textarea-autosize]]
            [reagent-material-ui.core.toolbar :refer [toolbar]]
            [reagent-material-ui.core.typography :refer [typography]]
            [reagent-material-ui.core.table :refer [table]]
            [reagent-material-ui.core.table-body :refer [table-body]]
            [reagent-material-ui.core.table-cell :refer [table-cell]]
            [reagent-material-ui.core.table-container :refer [table-container]]
            [reagent-material-ui.core.table-footer :refer [table-footer]]
            [reagent-material-ui.core.table-head :refer [table-head]]
            [reagent-material-ui.core.table-row :refer [table-row]]
            [reagent-material-ui.core.paper :refer [paper]]
            [cljs.core.async :as async]
            ["@material-ui/core" :as mui]
            [ajax.core :as ajax]
            [clojure.string :as string]
            ))

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

(defn dopple [response])

(def c (async/chan))

(async/go-loop []
               (let [item (async/<! c)
                     keyword (:keyword item)
                     domain (:domain item)]
                 (ajax/GET "/api/dopple"
                           {:params  {:keyword keyword
                                      :domain  domain}
                            :handler (fn [response] (do (.log js/console (str response))))}))
               (recur))

(def available? (complement string/blank?))

(defn get-similar-keywords []
  (let [keyword @search-keyword
        domain @search-domain]
    (when (and (available? keyword) (available? domain))
      (async/put! c {:keyword keyword :domain domain}))))

(available? "aa")

(defn keyword-field []
  [text-field
   {:value       @search-keyword
    :label       "label"
    :input-props {:style {:text-align "center"}}
    :style       {:width 500}
    :on-change   (fn [e]
                   (reset! search-keyword (event-value e))
                   (get-similar-keywords))
    :variant     "outlined"}
   ])

(defn autocomplete-domain [label]
  [autocomplete {:options         @domains
                 :style           {:width 250}
                 :on-input-change (fn [e v]
                                    (.log js/console v)
                                    (reset! search-domain v)
                                    (get-similar-keywords))
                 :render-input    (fn [^js params]
                                    (set! (.-variant params) "outlined")
                                    (set! (.-label params) label)
                                    (r/create-element mui/TextField params))}])


(defn search-bar []
  [grid {:container "true" :direction "column" :justify "center" :alignItems "center"}
   [typography {:color "primary" :align "center" :component "h2"} "I wanna use "]
   (keyword-field)
   [typography {:color "primary" :component "h2"} "in "]
   (autocomplete-domain "domain")])

(defn main-page []
  [:section.section>div.container>div.content
   [:<>
    (search-bar)]])

