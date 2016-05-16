(ns ttt-clojure.mini-max-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.mini-max :as mm]
            [ttt-clojure.game :as game]))

(describe "calculate"
  (it "returns the next viable move"
      (let [board ["X" "X" "O"
                   "O" "X" "O"
                   "X" "O" " "]
            game (game/create-new-game board "X" "O")]
      (should=
               8
               (mm/calculate game "X")))))
