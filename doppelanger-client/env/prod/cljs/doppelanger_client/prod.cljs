(ns doppelanger-client.prod
  (:require
    [doppelanger-client.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
