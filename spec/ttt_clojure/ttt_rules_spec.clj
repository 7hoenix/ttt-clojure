(ns ttt-clojure.ttt-rules-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.ttt-rules :as ttt]))

(describe "ttt rules"
  (it "ends the game if its over"
      (let [board [" " " " "O"
                   " " "O" " "
                   "O" " " " "]
            available-spaces [0 1 3 5 7 8]]
        (should=
          true
          (ttt/game-is-over? board available-spaces))))

  (it "does not end the game if its not over"
      (let [board ["X" " " " "
                   " " "X" " "
                   " " " " " "]
            available-spaces [1 2 3 5 6 7 8]]
        (should=
          false
          (ttt/game-is-over? board available-spaces))))

  (it "makes sure that the sequence has to be the same symbol"
      (let [board [" " " " "X"
                   " " "O" " "
                   "O" " " " "]
            available-spaces [0 1 3 5 7 8]]
        (should=
          false
          (ttt/game-is-over? board available-spaces))))

  (it "allows either player to win the game"
      (let [board ["X" " " " "
                   " " "X" " "
                   " " " " "X"]
            available-spaces [1 2 3 5 6 7]]
        (should=
          true
          (ttt/game-is-over? board available-spaces))))

  (it "handles a cats game"
      (let [board ["X" "X" "O"
                   "O" "X" "X"
                   "O" "O" "X"]
            available-spaces []]
        (should=
          true
          (ttt/game-is-over? board available-spaces))))

  (it "returns the winner"
    (let [board [" " "O" "X"
                 "O" "X" "O"
                 "X" "X" "O"]]
      (should=
        "X"
        (ttt/winner board)))))
