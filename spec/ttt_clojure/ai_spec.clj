(ns ttt-clojure.ai-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.ai :as ai]
            [ttt-clojure.ttt-rules :as ttt]
            [ttt-clojure.game :as game]))

(describe "ai"
  (around [it]
          (with-out-str (it)))

  (it "can play against itself"
    (let [game (game/create-new-game "X" "O")
          end-board (game/start game "X" "O" println ai/minimax)]
      (should=
              false
              (ttt/winner end-board)))))
