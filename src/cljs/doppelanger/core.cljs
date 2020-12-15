(ns doppelanger.core
  (:require  [reagent.core :as r]
             [reagent.dom :as rdom]
             [reagent-material-ui.core.grid :refer [grid]]
             [reagent-material-ui.core.menu-item :refer [menu-item]]
             [reagent-material-ui.core.text-field :refer [text-field]]
             [reagent-material-ui.core.textarea-autosize :refer [textarea-autosize]]
             [reagent-material-ui.core.toolbar :refer [toolbar]]))

(enable-console-print!)

(set! *warn-on-infer* true)

(defn event-value
  [^js/Event e]
  (.. e -target -value))


(defonce text-state (r/atom "scala"))

(defn main []
  [:<>
      [grid
       {:item true
        :xs   6}
       [text-field
        {:value       @text-state
         :label       "Select"
         :placeholder "Placeholder"
         :on-change   (fn [e]
                        (reset! text-state (event-value e)))
         :select      true}
        [menu-item
         {:value "scala"}
         "Scala"]
        [menu-item
         {:value "clojure"}
         "Clojure"]]]
   [grid
    {:item true
     :xs 10}
    [text-field]]])

(defn render []
  (rdom/render [main] (js/document.getElementById "app")))
