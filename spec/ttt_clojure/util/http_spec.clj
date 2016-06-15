(ns ttt-clojure.util.http-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.util.http :as http]))

(describe "Basic Response"
  (it "returns a basic map with a given body"
      (should=
               {:status 200
                :headers {"Content-Type" "text/html"}
                :body "cake"}
               (http/basic-response "cake"))))

(describe "Not Found Response"
  (it "returns a not found map"
      (should=
        {:status 404
         :headers {"Content-Type" "text/html"}
         :body "cake"}
        (http/not-found "cake"))))
