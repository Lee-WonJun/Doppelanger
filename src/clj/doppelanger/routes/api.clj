(ns doppelanger.routes.api
  (:require
    [doppelanger.layout :as layout]
    [doppelanger.db.core :as db]
    [clojure.java.io :as io]
    [doppelanger.middleware :as middleware]
    [ring.util.response]
    [ring.util.http-response :as response]
    [clojure.string :as string]
    [doppelanger.validation :as validation]))

(def available? (comp string/lower-case string/trim))

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


(defn api-routes []
  ["/api"
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/domain" {:get (fn [_] {:body (get-every-domain)})}]
   ["/dopple" {:get (fn [{{domain :domain keyword :keyword} :params}]
                      (let [reply (search-dopple domain keyword)
                            _ (println reply)]
                        {:body reply}))}]])

