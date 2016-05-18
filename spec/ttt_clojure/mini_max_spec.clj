(ns ttt-clojure.mini-max-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.mini-max :as mm]
            [ttt-clojure.game :as game]))

(describe "minimax"
  (it "chooses a forcing move"
    ; (let [board [" " "X" " "
    ;              "O" "O" "X"
    ;              "X" "O" " "]]
    (let [board [" " "X" "O"
                 " " " " "X"
                 " " " " "X"]]
      (should=
               2
               (mm/minimax board "X" "O")))))


















































  ; (it "returns the next viable move"
  ;     (let [board ["X" "X" "O"
  ;                  "O" "X" "O"
  ;                  "X" "O" " "])
  ;     (should=
  ;              8
  ;              (mm/minimax board "X" "O")))))

  ; (it "returns a different viable move"
  ;     (let [board ["X" "X" " "
  ;                  "O" "X" "O"
  ;                  "X" "O" "O"]
  ;           game (game/create-new-game board "X" "O")]
  ;     (should=
  ;              2
  ;              (mm/minimax game "X"))))

  ; (it "chooses the winning move if two are available"
  ;     (let [board ["O" "O" "X"
  ;                  "X" "X" " "
  ;                  " " "O" "O"]
  ;           invoking-player "O"
  ;           other-player "X"]
  ;     (should=
  ;              6
  ;              (mm/calculate board
  ;                            invoking-player
  ;                            other-player))))


; (describe "score"
;   (it "returns 10 for a win this turn"
;     (let [board ["X" "X" "O"
;                  "O" "X" "O"
;                  "X" "O" "X"]]
;           (should=
;                    10
;                    (mm/score board "X" "X"))))

;   (it "returns -10 for your opponent winning this turn"
;     (let [board ["X" "X" "O"
;                  "O" "X" "O"
;                  "X" "O" "O"]]
;           (should=
;                    -10
;                    (mm/score board "X" "O")))))
