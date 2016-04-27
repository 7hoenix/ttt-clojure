(ns ttt-clojure.board-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.board :as sut]))

(describe "Board"
          (it "works with 1x1 dimensions"
              (let [board (sut/new-board 1)]
                (should= sut/spots-available 1))))
