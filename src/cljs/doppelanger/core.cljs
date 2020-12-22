(ns doppelanger.core
  (:require  [reagent.core :as r]
             [reagent.dom :as rdom]
             [reagent-material-ui.core.grid :refer [grid]]
             [reagent-material-ui.core.menu-item :refer [menu-item]]
             [reagent-material-ui.core.text-field :refer [text-field]]
             [reagent-material-ui.core.textarea-autosize :refer [textarea-autosize]]
             [reagent-material-ui.core.toolbar :refer [toolbar]]
             [cljs.core.async :as async]
             [ajax.core :as ajax]
             [cljs-http.client :as http]
             ))

(enable-console-print!)

(set! *warn-on-infer* true)

(defn event-value
  [^js/Event e]
  (.. e -target -value))

(defonce domains (r/atom ["scala" "clojure" "fsharp" "csharp"]))
(defonce start-domain (r/atom "scala"))
(defonce goal-domain (r/atom "clojure"))
(defonce search-keyword (r/atom "" ))
(defonce goal-keyword (r/atom []))

(defn load-domains! []
  (ajax/GET "/domain"
            {:handler (fn [response] (do (.log js/console (str response))
                                         (reset! domains response)))}))

(def c (async/chan))

(async/go-loop []
               (let [x (async/<! c)]
                 (println "Got a value in this loop:" x)
                 (ajax/GET "/dopple" {:params {:start_domain @start-domain
                                               :keyword @search-keyword
                                               :goal_domain @goal-domain }}))
               (recur))


(defn get-similar-keywords [keyword]
  (async/put! c keyword) )



(defn domain-field [domain-atom]
  [text-field
   {:value       @domain-atom
    :label       "Select"
    :placeholder "Placeholder"
    :on-change   (fn [e]
                   (reset! domain-atom (event-value e)))
    :select      true
    :Select-props { :native true }
    :variant "outlined"}
   (map #(vec [:option {:key % :value %} %]) @domains)
   ])

(defn main []
  [:<>
   [grid {:container true :spacing 3}
   [grid
    {:item true
     :xs   2}
    (domain-field start-domain) ]
    [grid
     {:item true
      :xs 8}
     [text-field {:value       @search-keyword
                  :placeholder "Search"
                  :on-change   #(do (reset! search-keyword (event-value %)) (get-similar-keywords (event-value %)))}]]
    [grid
     {:item true
      :xs   6}
     (domain-field goal-domain )]]])

(defn render []
  (load-domains!)
  (rdom/render [main] (js/document.getElementById "app")))
