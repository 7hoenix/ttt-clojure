(ns ttt-clojure.ai
  (:require [ttt-clojure.mini-max :as mm]))

(defn minimax [board current-player opponent-player printer]
  {:location (mm/minimax board
                         current-player
                         opponent-player)
   :player current-player})
