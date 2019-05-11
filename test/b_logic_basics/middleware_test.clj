; Licensed under GPL-3

(ns b-logic-basics.middleware-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [b-logic-basics.testing.app-test :refer [app]]))

(deftest wrap-slurp-body
  (testing "when a handler requires a request body"
    (testing "and a body is provided"
      (let [response (app (mock/request :post "/echo" "Echo!"))]
        (testing "the status code is 200"
          (is (= 200 (:status response)))
          (testing "with the request body in the response body"
            (is (= "Echo!" (:body response)))))))
    (testing "and a body isn't provided"
      (let [response (app (mock/request :get "/echo"))]
        (testing "the status code is 400"
          (is (= 400 (:status response))))))))

(deftest wrap-json
  (testing "the /clojurify endpoint"
    (testing "when provided with valid JSON"
      (let [example-map {"hello" "json"}
            example-json (json/encode example-map)
            response (app (-> (mock/request :post "/clojurify" example-json)
                              (mock/content-type "application/json")))]
        (testing "returns a 200"
          (is (= 200 (:status response)))
          (testing "with a clojure map in the body"
            (is (= (str example-map) (:body response)))))))
    (testing "when provided with invalid JSON"
      (let [response (app (-> (mock/request :post "/clojurify" ";!;")
                              (mock/content-type "application/json")))]
        (testing "returns a 400"
          (is (= 400 (:status response))))))))

(deftest wrap-500-catchall
  (testing "when a handler throws an exception"
    (let [response (app (mock/request :get "/trouble"))]
      (testing "the status code is 500"
        (is (= 500 (:status response))))
      (testing "and the body only contains the exception message"
        (is (= "Divide by zero" (:body response)))))))