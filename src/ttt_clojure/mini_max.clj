(ns ttt-clojure.mini-max
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.ttt-rules :as ttt]
            [ttt-clojure.game :as game]))

(defn calculate ([game player] (calculate game
                                        (:board game)
                                        0
                                        player))
  ([game board depth player]
    (let [available (board/available-spaces board)
          next-move (first available)
          board (board/make-move board
                                 next-move
                                 player)]
      ; (println (str "available: " available " next-move: " next-move " board: " board))
      (loop [board board
             next-move next-move
             player player
             depth depth]
        (let [available (board/available-spaces board)]
          ; (println (str "game is over? " (ttt/game-is-over? board available)))
          (if (ttt/game-is-over? board available)
            next-move
            (let [next-board (board/make-move
                               board
                               next-move
                               player)
                  next-move (first available)
                  player (game/get-other-player game player)
                  new-depth (- depth 1)]
              (recur next-board
                     next-move
                     player
                     new-depth))))))))
          ; (println (str "next move: " next-move " depth: " depth))
