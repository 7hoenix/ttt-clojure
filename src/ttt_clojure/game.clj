(ns ttt-clojure.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.cli :as cli]))

(defn- make-move [board move]
  (board/make-move
    board
    (:location move)
    (:symbol move)))

(defn game-loop ([board] game-loop board cli/prompt-move)
  ([board func]
  (let [move (func board)
        board (make-move board move)]
  (while (not (board/game-over? board))
    (game-loop board func)
    )
  true)))
