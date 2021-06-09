(ns doppelanger.routes.api
  (:require
    [doppelanger.layout :as layout]
    [doppelanger.db.core :as db]
    [clojure.java.io :as io]
    [doppelanger.middleware :as middleware]
    [ring.util.response]
    [ring.util.http-response :as response]
    [clojure.string :as string]))

(defn pass [x]
  (println x)
  x)


(def available? (comp string/lower-case string/trim))

(defn get-every-domain [] (map :domain (db/get-domain)))

(defn search-dopple [domain keyword]
  (as->  {:keyword keyword} $
         (db/get-keyword-id $)
         (map :keywordid $)
         (distinct $)
         (mapcat #(db/get-relation-groups {:keyword_id %}) $)
         (distinct $)
         (map :relationgroup $)
         (map #(db/get-dopple-keyword {:relation_group % :domain domain}) $)
         (map :keyword $)))


(defn api-routes []
  ["/api"
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/domain" {:get (fn [_] {:body (get-every-domain)})}]
   ["/dopple" {:get (fn [{{domain :domain keyword :keyword} :params}]
                      (let [_ (println domain keyword)
                            reply (search-dopple domain keyword)]
                        {:body reply}))}]])

