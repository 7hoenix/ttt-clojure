(ns ttt-clojure.jpetty-main
  (:require [ttt-clojure.router :as router]
            [ttt-clojure.web.ttt :as ttt]
            [adapter.core :as adapter])
  (:gen-class))

(defn -main []
  (adapter/run-jpetty (router/app ttt/routes)))
