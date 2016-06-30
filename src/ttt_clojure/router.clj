(ns ttt-clojure.router
  (:require [ring.middleware.reload :as reload]
            [ring.middleware.params :as params]
            [ring.middleware.resource :as resource]
            [ring.middleware.content-type :as content]
            [ring.middleware.not-modified :as not-modified]
            [ring.middleware.json :as json]))

(defn identify-route [uri]
  (re-find #"\D+" uri))

(defn make-routes [routes]
  (fn [request]
    (let [route (get-in routes
                        [(:request-method request)
                         (identify-route (:uri request))]
                        (:not-found routes))]
      (route request))))

(defn app [routes]
  (-> (make-routes routes)
      (params/wrap-params)
      (resource/wrap-resource "public")
      (content/wrap-content-type)
      (not-modified/wrap-not-modified)
      (json/wrap-json-response)
      (reload/wrap-reload)))
