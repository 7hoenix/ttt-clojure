(ns ttt-clojure.mini-max
  (:require [ttt-clojure.board :as board]
            [ttt-clojure.ttt-rules :as ttt]))

(declare minimax get-best-score)

(defn score-complete-board [board depth player opponent]
  (condp = (ttt/winner board)
    player (- 10 depth)
    opponent (- depth 10)
    0))

(defn score-incomplete-board [board player opponent]
    (let [player-adv-count (ttt/advantage-count board player)
          opponent-adv-count (ttt/advantage-count board opponent)]
      (- (* player-adv-count 0.5) (* opponent-adv-count 0.5))))

(defn analyze [board depth player opponent]
  (if (ttt/game-is-over? {:board board})
    (score-complete-board board depth player opponent)
    (score-incomplete-board board player opponent)))

(defn minimax [board depth player opponent location]
  (let [updated-board (board/make-move board location player)]
    (if (or (> depth 2) (ttt/game-is-over? {:board updated-board}))
      [(analyze updated-board depth player opponent) location]
      [(* -1 (first (get-best-score updated-board (+ 1 depth) opponent player))) location])))

(defn get-best-score [board depth player opponent]
  (apply max-key first (map
                         (partial minimax board depth player opponent)
                         (board/available-spaces board))))

(defn get-minimax-move [board player opponent]
  (second (get-best-score board 0 player opponent)))
