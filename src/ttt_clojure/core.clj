(ns ttt-clojure.core
  (:require [ttt-clojure.game :as game])
  (:gen-class))

(defn -main []
  (game/start))
