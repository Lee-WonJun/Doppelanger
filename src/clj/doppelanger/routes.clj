(ns doppelanger.routes
  (:require [clojure.java.io :as io]
            [compojure.core :refer [ANY GET PUT POST DELETE routes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [response]]
            [cheshire.core :as json]))


(defn json [x]
  (-> x
      json/generate-string
      response
      (assoc :headers {"Content-Type" "application/json; charset=utf-8"})))

(defn pass [x]
  (println x)
  x)

(defn search-keyword [start-domain keyword goal-domain]
  (rand-nth [ (vector {:domain "csharp" :keyword "Select" :description "Linq Select" :pros 30 :cons 50 :uuid 12345 }
                       { :domain "csharp" :keyword "Select" :description "Linq Select, signature is (T item, int index)" :pros 55 :cons 4 :uuid 54321})
              (vector {:domain "fsharp" :keyword "map" :description "Linq Select" :pros 30 :cons 50 :uuid 12345 }
                       { :domain "fsharp" :keyword "Seq.map" :description "Linq Select, signature is (T item, int index)" :pros 55 :cons 4 :uuid 54321})])
 )

(defn home-routes [endpoint]
  (routes
   (GET "/" _
     (-> "public/index.html"
         io/resource
         io/input-stream
         response
         (assoc :headers {"Content-Type" "text/html; charset=utf-8"})
         pass ))
   (GET "/domain" [] (pass (json ["all" "scala" "clojure" "fsharp" "csharp" "cpp"])))
   (GET "/dopple" [start-domain keyword goal-domain] (-> (search-keyword start-domain keyword goal-domain)
                                                         (json)
                                                         (pass)))
   (resources "/")))
