(ns ttt-clojure.ui-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.ui :as ui]))

(describe "user interface"
  (around [it]
          (with-out-str (it)))

  (it "reports the winner"
    (let [printer println]
      (should=
        "X wins"
        (ui/report-winner printer "X")))))
