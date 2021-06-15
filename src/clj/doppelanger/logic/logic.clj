(ns doppelanger.logic.logic
  (:require
    [doppelanger.validation :as validation]
    [doppelanger.db.core :as db]))

(defn get-every-domain [] (map :domain (db/get-domain)))

(defn search-dopple [domain keyword]
  (->> {:keyword (validation/keyword-postprocessing keyword)}
       (db/get-keyword-id)
       (map :keywordid)
       (distinct)
       (mapcat #(db/get-relation-groups {:keyword_id %}))
       (distinct)
       (map :relationgroup)
       (map #(db/get-dopple-keyword {:relation_group % :domain domain}))
       (filter some?)))


(def get-domain (fn [_] {:body (get-every-domain)}))

(def get-dopple (fn [{{domain :domain keyword :keyword} :params}]
                  (let [reply (search-dopple domain keyword)
                        _ (println reply)]
                    {:body reply})))

(def get-relation (fn [_]))
