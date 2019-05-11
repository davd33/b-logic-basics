; Licensed under GPL-3

(ns b-logic-basics.testing.app-test
  (:require [compojure.core :refer [ANY GET POST routes]]
            [b-logic-basics.http.middleware :as mid]
            [b-logic-basics.testing.handler-test :refer :all]))

(def app-routes
  (routes
    (GET "/trouble" [] (/ 1 0))
    (ANY "/echo" [] body-echo-handler)
    (POST "/clojurify" [] (mid/wrap-json handle-json2edn))))

(def app
  (-> app-routes
      mid/wrap-slurp-body
      mid/wrap-500-catchall))
