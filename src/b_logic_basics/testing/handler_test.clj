; Licensed under GPL-3

(ns b-logic-basics.testing.handler-test
  (:require [ring.util.response :as resp]))

(defn body-echo-handler
  [request]
  (if-let [body (:body request)] ; not all requests have bodies
    (-> (resp/response body)
        (resp/content-type "text/plain")
        (resp/charset "utf-8"))
    ; if no body, we'll fire an 400
    (-> (resp/response "You must submit a body with your request.")
        (resp/status 400))))

(defn handle-json2edn
  [request]
  (-> (:body request)
      str
      resp/response
      (resp/content-type "application/edn")))
