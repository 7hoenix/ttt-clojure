(ns ttt-clojure.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.cli :as cli]))

(defn- make-move [board move]
  (board/make-move 
    board 
    (:location move)
    (:symbol move)))

(defn game-loop [board]
  (let [move (cli/prompt-move board)
        board (make-move board move)]
  (if (board/game-over? board)
    true
    (game-loop board)
    )))
