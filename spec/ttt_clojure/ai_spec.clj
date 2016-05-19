(ns ttt-clojure.ai-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.ai :as ai]
            [ttt-clojure.game :as game]))

(describe "ai"
  (it "can play against itself"
    (let [game (game/create-new-game "X" "O")
          end-board (game/start game println ai/minimax)]
      (should=
              false
              (ttt/winner board)))))
