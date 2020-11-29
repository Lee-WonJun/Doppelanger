(ns doppelanger-client.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]
      [antizer.reagent :as ant]))

;; -------------------------
;; Views

(defn combo []
   [ant/select {:showSearch true :default-value "scala" :style {:width 150 :padding "10px"}}
   [ant/select-option {:value "scala"} "scala"]
   [ant/select-option {:value "clojure"} "clojure"]
   [ant/select-option {:value "haskell"} "haskell"]
   [ant/select-option {:value "fsharp"} "F#"]
   [ant/select-option {:value "csharp"} "C#"]
   [ant/select-option {:value "cpp"} "C++"]])

(defn keyword-select []
  [ant/input {:margin 0}])


(defn home-page []
  [:div
   [combo]
   [keyword-select]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
