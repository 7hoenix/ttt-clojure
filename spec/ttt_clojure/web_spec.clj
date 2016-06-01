(ns ttt-clojure.web-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.web :as web]
            [clojure.string :as string]
            [ring.mock.request :as req]))

(describe "/"
  (it "displays a message about starting a new game"
    (should=
      true
      (string/includes?
        (:body (web/app (req/request :get "/")))
        "start a new game?"))))
