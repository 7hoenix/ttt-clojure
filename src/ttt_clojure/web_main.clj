(ns ttt-clojure.web-main
  (:require [ttt-clojure.ring-adapter :as adapter]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn -main []
  (jetty/run-jetty #'adapter/app {:port 8383 :join? false}))
