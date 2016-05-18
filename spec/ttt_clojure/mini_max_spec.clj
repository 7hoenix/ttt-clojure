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
                 (mm/minimax board "X" "O")))))
