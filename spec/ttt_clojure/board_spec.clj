(ns ttt-clojure.board-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.board :as sut]))

(describe "Board"
  (context "with 1x1 dimensions"
    (let [board (sut/new-board 1)]
      (it "shows 1 spot available"
            (should= 1 (count (sut/spots-available board))))))
  (context "with 2x2 dimensions"
    (let [board (sut/new-board 2)]
      (it "shows 4 spots available"
            (should= 4 (count (sut/spots-available board))))))
  (context "with 3x3 dimensions"
    (let [board (sut/new-board 3)]
      (it "shows 4 spots available"
            (should= 9 (count (sut/spots-available board)))))))
