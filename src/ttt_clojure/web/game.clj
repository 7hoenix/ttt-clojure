(ns ttt-clojure.web.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.basic-game :as basic-game]
            [ttt-clojure.ttt-rules :as ttt]
            [ttt-clojure.player :as player]))

(defn get-move [game]
   (if (ttt/game-is-over? (:board game))
     false
     (player/take-turn (:player2 game) (:board game))))
