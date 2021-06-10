(ns doppelanger.table.table
  (:require
    [reagent.core :as r]
    [cljsjs/material-table]
    ))

(defonce table-data (r/atom []))

(def MaterialTable (r/adapt-react-class (aget js/MaterialTable "default")))
(defn material-table []
  [MaterialTable {:title   "result"
                  :style       {:width 1000
                                :margin-top 40}
                  :columns [{:title "Keyword" :field :keyword}
                            {:title "More Info" :field :info}]
                  :data    @table-data}])

(defn reset-tabel! [data]
  (reset! table-data data))