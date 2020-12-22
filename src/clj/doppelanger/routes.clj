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

(defn home-routes [endpoint]
  (routes
   (GET "/" _
     (-> "public/index.html"
         io/resource
         io/input-stream
         response
         (assoc :headers {"Content-Type" "text/html; charset=utf-8"})
         pass ))
   (GET "/domain" [] (pass (json ["scala" "clojure" "fsharp" "csharp" "cpp"])))
   (GET "/dopple" [start-domain keyword goal-domain] (str start-domain keyword goal-domain))
   (resources "/")))
