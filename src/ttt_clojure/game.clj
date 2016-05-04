(ns ttt-clojure.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.cli :as cli]))

(defn update-board [])

(defn start []
   ; while the game is not over
   ; call take-next-move with the other player

  (loop [current-player starting-player
         board (board/create)]
    (let [move (get-move current-player)
          new-board (board/make-move board move current-player)]

      (take-next-move [new-board])
      (recur (find-opposite-player current-player) new-board )
  )

(defn take-next-move [board]
  )
