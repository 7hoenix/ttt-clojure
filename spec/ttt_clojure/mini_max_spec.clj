(ns ttt-clojure.mini-max-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.mini-max :as mm]))

(describe "mini max"
  (it "finds a trivial solution"
      (let [board ["X" "X" "O"
                   "O" "X" "X"
                   "O" "O" " "]
            maxi-player "X"]
        (should= 8
                 (mm/mini-max board maxi-player)))))
