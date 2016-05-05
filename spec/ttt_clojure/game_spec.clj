(ns ttt-clojure.game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.game :as game]
            [ttt-clojure.cli :as cli]
            [ttt-clojure.board :as board]))

(describe "game loop"
  (with-stubs)

  (it "ends the game if there is a winner"
      (with-redefs [cli/report-winner (fn [_] "X wins")
                    cli/prompt-move (fn [_ _]
                                      {:location 0
                                       :player "X"})]
        (should=
          "X wins"
          (-> [" " " " " "
               " " "X" " "
               " " " " "X"]
              (game/start "X")))))

  (it "ends the game if it is tied"
      (with-redefs [cli/report-tie (fn [_] "Cats game")
                    cli/prompt-move (fn [_ _]
                                      {:location 0
                                       :player "O"})]
        (should=
          "Cats game"
          (-> [" " "X" "O"
               "O" "O" "X"
               "X" "O" "X"]
              (game/start "O")))))

  (it "prompts the next player for their next move"
      (let [game (game/new-game "X" "O")]
      (should=
        "X"
        (game/take-next-turn game "O"))))

  (it "prompts the next player for their next move"
      (let [game (game/new-game "X" "O")]
      (should=
        "O"
        (game/take-next-turn game "X")))))




























  ;     ; with-redefs -> get-move
  ;     ; you were called with the correct next player
  ;   )

      ; (let [cli (stub cli/report {:invoke game/test-method :return "puddy"})]
      ;   (should= 
      ;     "cake"
      ;     (cli "cake"))
      ;   )

