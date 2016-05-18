(ns ttt-clojure.mini-max
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.ttt-rules :as ttt]
            [ttt-clojure.board :as board]))

; board goes through each potential move and creates a new board representing that play
; for each of these board states we go through and calculate each potential move
; Keep going until we reach a terminating board (a board that ends in a win/loss/draw).

(declare maxi mini)

(defn score [board maximizing-player current-player]
  (cond
    (and (= maximizing-player current-player)
         (= (ttt/winner board) current-player)) 10
    (and (not (= maximizing-player current-player))
         (= (ttt/winner board) current-player)) -10
    :else 0))

(defn maxi [board available moves-with-scores maxi-player mini-player]
  (println (str "AVAILABLE MAX: " available))
  (let [new-board (board/make-move board (first available) maxi-player)]
    (println (str "maxi: board: " new-board " first : " (first available)))
    (println (ttt/game-is-over? new-board available))
    (println (first available))
    (println (score new-board maxi-player maxi-player))
    (let [moves-with-scores (assoc moves-with-scores (first available) (score new-board maxi-player maxi-player))]
    (println moves-with-scores))
    (if (ttt/game-is-over? new-board available)
      (assoc moves-with-scores
             (first available)
             (score new-board maxi-player maxi-player))
      (mini new-board (rest available) moves-with-scores maxi-player mini-player)))
  (println moves-with-scores))

(defn mini [board available moves-with-scores maxi-player mini-player]
  (println (str "AVAILABLE MIN: " available))
  (let [new-board (board/make-move board (first available) mini-player)]
    (println (str "mini: board: " new-board " first : " (first available)))
    (if (ttt/game-is-over? new-board available)
      (assoc moves-with-scores
             (first available)
             (score new-board maxi-player mini-player))
      (maxi new-board (rest available) moves-with-scores maxi-player mini-player)))
  (println moves-with-scores))
    ; (if (empty? available)
    ;   (println "cake")
    ;   (recur board (rest available) moves-with-scores maxi-player mini-player))))
      ; (println (str "from mini: " (score new-board maxi-player mini-player)))

(defn minimax [board maximizing-player minimizing-player]
    (println board)
  (let [available (board/available-spaces board)
        moves-with-scores {}]
    (maxi board
          available
          moves-with-scores
          maximizing-player
          minimizing-player)))

; Each parent needs to choose the branch that represents the best choice for that player.
; For the first level that player is choosing the maximum value returned.
; For the second level that player is choosing the minimum value returned (since those values
; are going to get passed as choices up to the other player).
; For the third level that player is choosing the maximum value returned for each board.
; Once we have reached terminating board positions those results need to be passed up the tree
; to the parent...
; The parent needs to look at the return values and save the very highest or lowest score to
; go be passed up to its parent based on which players interest it is representing.





















































; (defn score [board invoking-player current-player]
;   (cond
;     (and (= invoking-player current-player)
;          (= (ttt/winner board) current-player)) 10
;     (and (not (= invoking-player current-player))
;          (= (ttt/winner board) current-player)) -10
;     :else 0))

; (defn maxi [board states maximizing-player]
;   (let [available (board/available-spaces board)
;         move (first available)
;         states (conj states board)]
;       (println available)
;     (if (empty? available)
;       states
;       (recur (board/make-move board
;                               (first available)
;                               maximizing-player)
;              states
;              maximizing-player))))

; (defn mini [board moves minimizing-player]
;   (let [available (board/available-spaces board)
;         move (first available)
;         moves (conj moves move)]
;       (println available)
;     (if (empty? available)
;       moves
;       (recur (board/make-move board
;                               (first available)
;                               minimizing-player)
;              moves
;              minimizing-player))))

