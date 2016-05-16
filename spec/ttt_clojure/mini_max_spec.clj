(ns ttt-clojure.mini-max-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.mini-max :as mm]
            [ttt-clojure.game :as game]))

(describe "mini max"
  (it "finds a trivial solution"
      (let [board ["X" "X" "O"
                   "O" "X" "X"
                   "O" "O" " "]
            maxi-player "X"
            game (game/create-new-game board "X" "O")]
        (should= 8
                 (mm/calculate game maxi-player))))

  (it "finds a different trivial solution"
      (let [board ["O" "X" " "
                   "X" "O" "X"
                   "O" "O" "X"]
            maxi-player "X"
            game (game/create-new-game board "X" "O")]
        (should= 2
                 (mm/calculate game maxi-player))))

  (it "blocks the opponent from a trivial win"
      (let [board [" " "X" " "
                   "O" "O" "X"
                   "X" "O" "X"]
            maxi-player "O"
            game (game/create-new-game board "X" "O")]
        (should= 2
                 (mm/calculate game maxi-player))))

  (it "finds the win/win scenario"
      (let [board [" " "X" " "
                   "O" "O" "X"
                   "X" "O" " "]
            maxi-player "X"
            game (game/create-new-game board "X" "O")]
        (should= 2
                 (mm/calculate game maxi-player)))))
