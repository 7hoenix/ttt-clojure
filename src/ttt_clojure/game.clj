(ns ttt-clojure.game
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.cli :as cli]))

(defn new-game ([player1 player2] 
                (new-game (board/new-board) player1 player2))
  ([board player1 player2]))

(defn check-for-winner [game current-player]
  (cli/report-winner current-player))

















































; (defn new-game [player1 player2]
;   {:player1 player1
;    :player2 player2})

; (defn- find-opposite-player [game current-player]
;     (if (= (:player1 game) current-player)
;       (:player2 game)
;       (:player1 game)))

; (defn take-next-turn [game current-player]
;   (find-opposite-player game current-player))

; (defn start [board current-player]
;   (let [move (cli/prompt-move board current-player)
;         new-board (board/make-move board
;                                    (:location move)
;                                    (:player move))]
;     (if (board/game-over? new-board)
;       (if (board/tie? new-board)
;         (cli/report-tie current-player)
;         (cli/report-winner current-player)))))















































; (defn update-board [])

; (defn start []
;    ; while the game is not over
;    ; call take-next-move with the other player

;   (loop [current-player starting-player
;          board (board/create)]
;     (let [move (get-move current-player)
;           new-board (board/make-move board move current-player)]

;       (take-next-move [new-board])
;       (recur (find-opposite-player current-player) new-board )
;   )

; (defn take-next-move [board]
;   )
