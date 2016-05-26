(ns ttt-clojure.ai
  (:require [ttt-clojure.mini-max :as mm]
            [ttt-clojure.player :as player]))

(defn minimax [board current-player opponent-player]
  {:location (mm/get-minimax-move board
                                  current-player
                                  opponent-player)
   :player current-player})

(defrecord AI [mark opponent-mark]
  player/PPlayer
  (display [this _] nil)
  (take-turn [this board] (minimax board mark opponent-mark)))

(defn create-ai [mark opponent-mark]
  (->AI mark opponent-mark))
