(ns ttt-clojure.game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.game :as game]
            [ttt-clojure.board :as board]))

(describe "game loop"
  (it "ends the game if there is a winner"
      ; stub cli/report win
      ; assert it was called with the correct winner
    )

  (it "ends the game if it is tied"
      ; stub cli/report tie
      ; same thing
    )

  (it "prompts the next player for their next move"
      ; with-redefs -> get-move
      ; you were called with the correct next player
    )
)
