(ns doppelanger.routes.api
  (:require

    [doppelanger.middleware :as middleware]
    [ring.util.response]
    [doppelanger.logic.interface :as interface]))


(defn api-routes []
  ["/api"
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/domain" {:get interface/get-domain}]
   ["/dopple" {:get interface/get-dopple}]
   ["/relation" {:get interface/get-relation}]])
