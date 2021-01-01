(ns doppelanger.routes.api
  (:require
    [doppelanger.layout :as layout]
    [doppelanger.db.core :as db]
    [clojure.java.io :as io]
    [doppelanger.middleware :as middleware]
    [ring.util.response]
    [ring.util.http-response :as response]))

(defn pass [x]
  (println x)
  x)

(defn search-keyword [start-domain keyword goal-domain]
  (rand-nth [ (vector {:domain "csharp" :keyword "Select" :description "Linq Select" :pros 30 :cons 50 :uuid 12345 }
                      { :domain "csharp" :keyword "Select" :description "Linq Select, signature is (T item, int index)" :pros 55 :cons 4 :uuid 54321})
             (vector {:domain "fsharp" :keyword "map" :description "Linq Select" :pros 30 :cons 50 :uuid 12345 }
                     { :domain "fsharp" :keyword "Seq.map" :description "Linq Select, signature is (T item, int index)" :pros 55 :cons 4 :uuid 54321})])
  )

(defn api-routes []
  ["/api"
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/domain" {:get (fn [_] {:body ["scala" "clojure" "fsharp" "csharp" "cpp"]})}]
   ["/dopple" {:get (fn [{{start-domain :start_domain keyword :keyword goal-domain :goal_domain} :params}]
                      (let [_ (println start-domain keyword goal-domain)
                            reply (search-keyword start-domain keyword goal-domain)]
                        {:body reply}))}]])

