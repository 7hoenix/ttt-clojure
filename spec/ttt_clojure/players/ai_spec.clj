(ns ttt-clojure.players.ai-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.players.ai :as ai]
            [ttt-clojure.ttt-rules :as ttt]
            [ttt-clojure.game :as game]))

(describe "ai"
  (around [it]
          (with-out-str (it)))

  (it "can play against itself"
    (let [game (game/create-new-game (ai/create-ai "X" "O")
                                     (ai/create-ai "O" "X"))
          end-board (game/start game)]
      (should=
              false
              (ttt/winner end-board)))))
