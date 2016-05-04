(ns ttt-clojure.game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.game :as game]
            [ttt-clojure.cli :as cli]
            [ttt-clojure.board :as board]))

(defn- make-moves [b mark locations]
  (reduce #(board/make-move % %2 mark) b locations))

(describe "game loop"
  (with-stubs)
  (with empty-board (board/new-board))

  (it "ends the game if there is a winner"
      (with-redefs [cli/report-winner (fn [_] "X wins")]
        (should=
          "X wins"
          (-> @empty-board
              (make-moves board/x-mark [0 4 8])
              (game/start "X"))))))

  ; (it "ends the game if it is tied"
  ;     ; stub cli/report tie
  ;     ; same thing
  ;   )

  ; (it "prompts the next player for their next move"
  ;     ; with-redefs -> get-move
  ;     ; you were called with the correct next player
  ;   )

      ; (let [cli (stub cli/report {:invoke game/test-method :return "puddy"})]
      ;   (should= 
      ;     "cake"
      ;     (cli "cake"))
      ;   )

