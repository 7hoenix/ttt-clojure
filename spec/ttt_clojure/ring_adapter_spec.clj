(ns ttt-clojure.ring-adapter-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.ring-adapter :as adapter]
            [ring.mock.request :as mock]))

(describe "Wrap route"
  (it "wraps the request with a route"
      (let [handler (fn [request] request)
            request (mock/request :get "/games/1")]
        (should=
                 "/games/"
                 (:route
                   ((adapter/wrap-route handler)
                    request))))))
