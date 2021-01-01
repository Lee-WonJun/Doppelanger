(ns doppelanger.home
  (:require  [reagent.core :as r]
             [reagent.dom :as rdom]
             [reagent-material-ui.core.grid :refer [grid]]
             [reagent-material-ui.core.menu-item :refer [menu-item]]
             [reagent-material-ui.core.text-field :refer [text-field]]
             [reagent-material-ui.core.textarea-autosize :refer [textarea-autosize]]
             [reagent-material-ui.core.toolbar :refer [toolbar]]
             [reagent-material-ui.core.table :refer [table]]
             [reagent-material-ui.core.table-body :refer [table-body]]
             [reagent-material-ui.core.table-cell :refer [table-cell]]
             [reagent-material-ui.core.table-container :refer [table-container]]
             [reagent-material-ui.core.table-footer :refer [table-footer]]
             [reagent-material-ui.core.table-head :refer [table-head]]
             [reagent-material-ui.core.table-row :refer [table-row]]
             [reagent-material-ui.core.paper :refer [paper]]
             [cljs.core.async :as async]
             [ajax.core :as ajax]
             ))

(defn event-value
  [^js/Event e]
  (.. e -target -value))

(defonce domains (r/atom ["scala" "clojure" "fsharp" "csharp"]))
(defonce start-domain (r/atom "all"))
(defonce goal-domain (r/atom "clojure"))
(defonce search-keyword (r/atom "" ))
(defonce goal-keywords (r/atom []))

(defn table-code [item-atom]  [table-container
                               [table-head
                                [table-row
                                 [table-cell "domain"]
                                 [table-cell "keyword"]]]
                               [table-body
                                (map #(vec [table-row [table-cell (get % "domain")] [table-cell (get % "keyword")]] ) @item-atom)]])

(def table-code-atom (r/atom (table-code goal-keywords)))

(defn load-domains! []
  (ajax/GET "/api/domain"
            {:handler (fn [response] (do (.log js/console (str response))
                                         (reset! domains response)))}))

(def c (async/chan))

(async/go-loop []
               (let [x (async/<! c)]
                 (ajax/GET "/api/dopple" {:params  {:start_domain @start-domain
                                                :keyword      @search-keyword
                                                :goal_domain  @goal-domain}
                                      :handler (fn [response] (do (.log js/console (str response))
                                                                  (reset! goal-keywords response)
                                                                  (reset! table-code-atom (table-code goal-keywords))))}))
               (recur))


(defn get-similar-keywords [keyword]
  (async/put! c keyword) )

(defn domain-field [domains domain-atom]
  [text-field
   {:value       @domain-atom
    :label       "Select"
    :placeholder "Placeholder"
    :on-change   (fn [e]
                   (reset! domain-atom (event-value e)))
    :select      true
    :Select-props { :native true }
    :variant "outlined"}
   (map #(vec [:option {:key % :value %} %]) domains)
   ])

(defn main-page []
  [:section.section>div.container>div.content
   [:<>
    [grid {:container true :spacing 3}
     [grid
      {:item true
       :xs   2}
      (domain-field (concat ["all"] @domains) start-domain)]
     [grid
      {:item true
       :xs   8}
      [text-field {:value       @search-keyword
                   :placeholder "Search"
                   :on-change   #(do (reset! search-keyword (event-value %)) (get-similar-keywords (event-value %)))}]]
     [grid
      {:item true
       :xs   6}
      (domain-field @domains goal-domain)]]
    @table-code-atom]])

