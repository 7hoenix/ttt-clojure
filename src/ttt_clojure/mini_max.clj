(ns ttt-clojure.mini-max
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.ttt-rules :as ttt]
            [ttt-clojure.board :as board]))

; (defn score [board invoking-player current-player]
;   (cond
;     (and (= invoking-player current-player)
;          (= (ttt/winner board) current-player)) 10
;     (and (not (= invoking-player current-player))
;          (= (ttt/winner board) current-player)) -10
;     :else 0))

(defn minimax [])

; board goes through each potential move and creates a new board representing that play
; for each of these board states we go through and calculate each potential move
; Keep going until we reach a terminating board (a board that ends in a win/loss/draw).
; Each parent needs to choose the branch that represents the best choice for that player.
; For the first level that player is choosing the maximum value returned.
; For the second level that player is choosing the minimum value returned (since those values
; are going to get passed as choices up to the other player).
; For the third level that player is choosing the maximum value returned for each board.
; Once we have reached terminating board positions those results need to be passed up the tree
; to the parent...
; The parent needs to look at the return values and save the very highest or lowest score to
; go be passed up to its parent based on which players interest it is representing.
