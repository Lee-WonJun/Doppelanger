(ns doppelanger.search
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [reagent-material-ui.core.grid :refer [grid]]
            [reagent-material-ui.core.menu-item :refer [menu-item]]
            [reagent-material-ui.core.text-field :refer [text-field]]
            [reagent-material-ui.lab.autocomplete :refer [autocomplete]]
            [reagent-material-ui.core.textarea-autosize :refer [textarea-autosize]]
            [reagent-material-ui.core.toolbar :refer [toolbar]]
            [reagent-material-ui.core.typography :refer [typography]]
            [cljs.core.async :as async]
            ["@material-ui/core" :as mui]
            [ajax.core :as ajax]
            [clojure.string :as string]
            [doppelanger.table.table :as table]
            [doppelanger.validation :as validation]
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

(defn dopple [response]
  (.log js/console response)
  (table/reset-tabel! response))


(def c (async/chan))

(async/go-loop []
               (let [item (async/<! c)
                     keyword (:keyword item)
                     domain (:domain item)]
                 (ajax/GET "/api/dopple"
                           {:params  {:keyword keyword
                                      :domain  domain}
                            :handler dopple}))
               (recur))

(def available? (complement string/blank?))

(defn get-similar-keywords []
  (let [keyword (validation/keyword-postprocessing @search-keyword)
        domain @search-domain]
    (when (and (available? keyword) (some #(= domain %) @domains))
      (async/put! c {:keyword keyword :domain domain}))))

(defn keyword-field []
  [text-field
   {:value       @search-keyword
    :label       "keyword"
    :input-props {:style {:text-align "center"}}
    :style       {:width 500}
    :on-change   (fn [e]
                   (reset! search-keyword (event-value e))
                   (get-similar-keywords))
    :variant     "outlined"}
   ])

(defn autocomplete-domain []
  [autocomplete {:options         @domains
                 :style           {:width 250}
                 :on-input-change (fn [e v]
                                    (.log js/console v)
                                    (reset! search-domain v)
                                    (get-similar-keywords))
                 :render-input    (fn [^js params]
                                    (set! (.-variant params) "outlined")
                                    (r/create-element mui/TextField params))}])


(defn search-bar []
  [grid {:container "true" :direction "column" :justify "center" :alignItems "center"}
   [typography {:color "primary" :align "center" :component "h2"} "I wanna use "]
   (keyword-field)
   [typography {:color "primary" :component "h2"} "in "]
   (autocomplete-domain)
   (table/material-table)])

(defn search-page []
  [:section.section>div.container>div.content
   [:<>
    (search-bar)]])

