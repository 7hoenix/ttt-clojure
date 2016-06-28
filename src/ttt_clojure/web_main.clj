(ns ttt-clojure.web-main
  (:require [ttt-clojure.router :as router]
            [ttt-clojure.web.ttt :as ttt]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn -main []
  (jetty/run-jetty (router/app ttt/routes) {:port 8383 :join? false}))
