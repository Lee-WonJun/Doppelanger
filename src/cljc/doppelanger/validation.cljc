(ns doppelanger.validation
  (:require [struct.core :as st]
            [clojure.string :as string]))

(defn keyword-postprocessing [keyword]
  (apply str (re-seq #"[a-zA-Z]" (string/lower-case keyword))))