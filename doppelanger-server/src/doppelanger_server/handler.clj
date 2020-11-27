(ns doppelanger-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn search [domain keyword target]
  (str domain keyword target))

(defn match [] "something")

(defroutes app-routes
           (GET "/" [] "Hello World")
           (GET "/search" [domain keyword target] (search domain keyword target))

           (POST "/match" {body :body}  body)
           (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes (assoc site-defaults :security false)))

