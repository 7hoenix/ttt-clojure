(ns ttt-clojure.ttt-rules-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.ttt-rules :as ttt]))

(describe "ttt rules"
  (it "ends the game if its over"
      (let [game {:board [" " " " "O"
                          " " "O" " "
                          "O" " " " "]}]
        (should=
          true
          (ttt/game-is-over? game))))

  (it "does not end the game if its not over"
      (let [game {:board ["X" " " " "
                          " " "X" " "
                          " " " " " "]}]
        (should=
          false
          (ttt/game-is-over? game))))

  (it "makes sure that the sequence has to be the same symbol"
      (let [game {:board [" " " " "X"
                          " " "O" " "
                          "O" " " " "]}]
        (should=
          false
          (ttt/game-is-over? game))))

  (it "allows either player to win the game"
      (let [game {:board ["X" " " " "
                          " " "X" " "
                          " " " " "X"]}]
        (should=
          true
          (ttt/game-is-over? game))))

  (it "handles a cats game"
      (let [game {:board ["X" "X" "O"
                          "O" "X" "X"
                          "O" "O" "X"]}]
        (should=
          true
          (ttt/game-is-over? game))))

  (it "returns the outcome of the game"
      (let [game {:board [" " "O" "X"
                          "O" "X" "O"
                          "X" "X" "O"]}]
      (should=
        "X"
        (ttt/outcome game))))

  (it "returns false if the game is tied"
      (let [game {:board ["X" "O" "X"
                          "O" "X" "X"
                          "O" "X" "O"]}]
      (should=
        false
        (ttt/outcome game)))))
