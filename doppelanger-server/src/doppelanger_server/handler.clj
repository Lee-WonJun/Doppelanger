(ns doppelanger-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
           (GET "/" [] "Hello World")
           (GET "/search" [domain keyword target] (str domain "," keyword "->" target))
           (GET "/user/:id" [id greeting]
             (str "<h1>" greeting " user " id "</h1>"))
           (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

