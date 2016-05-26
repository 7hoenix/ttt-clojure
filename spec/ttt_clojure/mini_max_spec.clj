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
                (mm/get-minimax-move board "X" "O"))))

  (it "basic"
      (let [board ["O" " " "X"
                   "X" " " " "
                   "X" "O" "O"]]
        (should=
                4
                (mm/get-minimax-move board "X" "O"))))

  (it "depth needed"
      (let [board [" " "X" " "
                   " " " " "X"
                   "O" "O" "X"]]
        (should=
                2
                (mm/get-minimax-move board "O" "X"))))

  (it "chooses a corner if you choose center"
      (let [board [" " " " " "
                   " " "X" " "
                   " " " " " "]]
        (should=
                8
                (mm/get-minimax-move board "O" "X")))))
