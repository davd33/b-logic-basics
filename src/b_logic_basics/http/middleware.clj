; Licensed under GPL-3

(ns b-logic-basics.http.middleware
  (:require [cheshire.core :as json]
            [ring.util.response :as resp])
  (:import (java.io InputStream)
           (com.fasterxml.jackson.core JsonParseException)))

(defn wrap-slurp-body
  "Middleware that returns a handler that slurps the body
  of the request when it is an instance of InputStream."
  [handler]
  (fn [request]
    (if (instance? InputStream (:body request))
      (let [prepared-request (update request :body slurp)]
        (handler prepared-request))
      (handler request))))

(defn wrap-json
  "Middleware, returns a handler with it's decoded json body.
  Otherwise returns 400."
  [handler]
  (fn [request]
    (if-let [json-request (try (update request :body json/decode)
                               (catch JsonParseException _ nil))]
      (handler json-request)
      (-> (resp/response "Sorry, the request's body shall be in JSON format.")
          (resp/status 400)))))

(defn wrap-500-catchall
  "Middleware that returns 500 if any exceptions are caught."
  [handler]
  (fn [request]
    (try (handler request)
         (catch Exception e (-> (resp/response (.getMessage e))
                                (resp/status 500)
                                (resp/content-type "text/plain")
                                (resp/charset "utf-8"))))))
