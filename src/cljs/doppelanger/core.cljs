(ns doppelanger.core
  (:require  [reagent.core :as r]
             [reagent.dom :as rdom]
             [reagent-material-ui.core.grid :refer [grid]]
             [reagent-material-ui.core.menu-item :refer [menu-item]]
             [reagent-material-ui.core.text-field :refer [text-field]]
             [reagent-material-ui.core.textarea-autosize :refer [textarea-autosize]]
             [reagent-material-ui.core.toolbar :refer [toolbar]]
             [cljs.core.async :as async]
             ))

(enable-console-print!)

(set! *warn-on-infer* true)

(defn event-value
  [^js/Event e]
  (.. e -target -value))

(defonce domains (r/atom ["Scala" "Clojure" "Fsharp" "Csharp"]))

(defn get-domains [] )

(def c (async/chan))

(async/go-loop []
         (let [x (async/<! c)]
           (println "Got a value in this loop:" x))
         (recur))


(defn get-similar-keywords [keyword]
      (async/put! c keyword) )

(defonce text-state (r/atom "Scala"))
(defonce search-keyword (r/atom "" ))

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
         :select      true
         :Select-props { :native true }
         :variant "outlined"}
        (map #(vec [:option {:key % :value %} %]) @domains)
        ]]
   [grid
    {:item true
     :xs 10}
    [text-field {:value       @search-keyword
                 :placeholder "Search"
                 :on-change   #(do (reset! search-keyword (event-value %)) (get-similar-keywords (event-value %)))}]]])

(defn render []
  (rdom/render [main] (js/document.getElementById "app")))

