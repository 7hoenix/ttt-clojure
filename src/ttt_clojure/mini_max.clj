(ns ttt-clojure.mini-max
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.ttt-rules :as ttt]
            [ttt-clojure.board :as board]))

(defn calculate [game player]
  (let [board (:board game)
        available (board/available-spaces board)
        next-move (first available)]
    (loop [board board
           next-move next-move
           available available
           player player]
      (let [new-board (board/make-move board next-move player)]
        (if (ttt/game-is-over? new-board available)
          next-move
          (recur board
                 (first available)
                 (rest available)
                 player))))))
