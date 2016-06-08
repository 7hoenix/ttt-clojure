(ns ttt-clojure.web-main
  (:require [ttt-clojure.server :as server]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn -main []
  (jetty/run-jetty #'server/app {:port 8383 :join? false}))
