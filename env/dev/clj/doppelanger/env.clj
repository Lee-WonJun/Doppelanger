(ns doppelanger.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [doppelanger.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[doppelanger started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[doppelanger has shut down successfully]=-"))
   :middleware wrap-dev})
