(ns ttt-clojure.mini-max-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.mini-max :as mm]))

(describe "minimax"
  (it "correctly figures out the forcing move"
      (let [board [" " "X" " "
                   "O" "O" "X"
                   "X" "O" " "]]
        (should=
                2
                (mm/minimax board "O" "X")))))

  ; (it "correctly figures out a different move"
  ;     (let [board [" " "X" " "
  ;                  "O" "O" "X"
  ;                  "X" "O" "O"]]
  ;       (should=
  ;               0
  ;               (mm/minimax board "O" "X"))))

  ; (it "basic"
  ;     (let [board ["O" " " "X"
  ;                  "X" " " " "
  ;                  "X" "O" "O"]]
  ;       (should=
  ;               4
  ;               (mm/minimax board "X" "O"))))

  ; (it "depth needed"
  ;     (let [board [" " "X" " "
  ;                  " " " " "X"
  ;                  "O" "O" "X"]]
  ;       (should=
  ;               2
  ;               (mm/minimax board "O" "X")))))
