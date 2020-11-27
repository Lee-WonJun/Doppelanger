(ns doppelanger-server.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [doppelanger-server.handler :refer :all]
            [cheshire.core :as json]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "search route"
    (let [query {:domain "clojure" :keyword "frequencies" :target "F#" }
          response (app (-> (mock/request :get "/search") (mock/query-string query)))]
      (is (= (:status response) 200))))

  (testing "post route"
    (let [[domain keyword target-domain target-keyword] ["haskell" "do-notation" "scala" "for-notation"]
          handler (-> (mock/body (mock/request :post "/match")
                                 (json/generate-string {:domain domain :keyword keyword :target-domain target-domain :target-keyword target-keyword}))
                      (mock/content-type "application/json"))
          _ (println handler)
          response (app handler)
          _ (println response)]
    (is (= (:status response) 200)))))
