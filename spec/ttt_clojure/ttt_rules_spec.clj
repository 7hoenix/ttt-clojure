(ns ttt-clojure.ttt-rules-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.ttt-rules :as ttt]))

(describe "ttt rules"
  (it "ends the game if its over"
      (let [board [" " " " "O"
                   " " "O" " "
                   "O" " " " "]]
        (should=
          true
          (ttt/game-is-over? board))))

  (it "does not end the game if its not over"
      (let [board ["X" " " " "
                   " " "X" " "
                   " " " " " "]]
        (should=
          false
          (ttt/game-is-over? board))))

  (it "makes sure that the sequence has to be the same symbol"
      (let [board [" " " " "X"
                   " " "O" " "
                   "O" " " " "]]
        (should=
          false
          (ttt/game-is-over? board))))

  (it "allows either player to win the game"
      (let [board ["X" " " " "
                   " " "X" " "
                   " " " " "X"]]
        (should=
          true
          (ttt/game-is-over? board))))

  (it "handles a cats game"
      (let [board ["X" "X" "O"
                   "O" "X" "X"
                   "O" "O" "X"]]
        (should=
          true
          (ttt/game-is-over? board))))

  (it "returns the outcome of the game"
      (let [board [" " "O" "X"
                   "O" "X" "O"
                   "X" "X" "O"]]
        (should=
          "X"
          (ttt/outcome board))))

  (it "returns false if the game is tied"
      (let [board ["X" "O" "X"
                   "O" "X" "X"
                   "O" "X" "O"]]
        (should=
          false
          (ttt/outcome board))))

  (it "returns the number of advantages"
      (let [board ["X" " " "X"
                   "O" "X" "X"
                   "O" "X" "O"]]
        (should=
          2
          (ttt/advantage-count board "X"))))

  (it "returns 0 if not an advantage"
      (let [board [" " "O" "X"
                   "O" "X" "X"
                   "O" "X" "O"]]
        (should=
          0
          (ttt/advantage-count board "X")))))
