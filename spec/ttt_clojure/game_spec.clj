(ns ttt-clojure.game-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.game :as game]
            [ttt-clojure.cli :as cli]
            [ttt-clojure.board :as board]))

(describe "game loop"
  (with-stubs)

  (it "ends the game if it has a winner"
      (with-redefs [cli/report-winner (fn [_] "X wins")]
        (let [board ["X" " " " "
                     " " "X" " "
                     " " " " "X"]
              current-player "X"
              game (game/create-new-game board current-player "O")]
      (should=
        "X wins"
        (game/start game current-player)))))

  (it "ends the game if it has a tie"
      (with-redefs [cli/report-tie (fn [] "Cats game")]
        (let [board ["X" "O" "X"
                     "X" "X" "O"
                     "O" "X" "O"]
              current-player "X"
              game (game/create-new-game board current-player "O")]
      (should=
        "Cats game"
        (game/start game current-player)))))

  ; (it "prompts the next player for a move if game is not over yet"
  ;   (with-redefs [cli/prompt-move (fn [_ _] {:location 0 :player "O"})]
  ;     (let [board [" " "O" "X"
  ;                  "X" "X" "O"
  ;                  " " "X" "O"]
  ;           current-player "X"
  ;           game (game/create-new-game board current-player "O")]
  ;       (should-invoke
  ;         game/get-next-move {:with [game current-player]} (game/start game current-player)))))

  (it "gets the next move for the player that isn't the current player"
      (with-redefs [cli/prompt-move (fn [_ _] {:location 0 :player "O"})]
        (let [board [" " "O" "X"
                     "X" "X" "O"
                     " " "X" "O"]
              current-player "X"
              game (game/create-new-game board current-player "O")]
          (should=
            {:location 0 :player "O"}
            (game/get-next-move game current-player)))))

  (it "makes a move on the board"
      (let [board [" " "O" "X"
                   "X" "X" "O"
                   " " "X" "O"]
            new-board [" " "O" "X"
                       "X" "X" "O"
                       "O" "X" "O"]
            move {:location 6 :player "O"}
            game (game/create-new-game board "X" "O")]
        (should=
          (conj game {:board new-board})
          (game/make-move game move)))))












































  ; (it "ends the game if there is a winner"
  ;     (with-redefs [cli/report-winner (fn [_] "X wins")
  ;                   cli/prompt-move (fn [_ _]
  ;                                     {:location 0
  ;                                      :player "X"})]
  ;       (should=
  ;         "X wins"
  ;         (-> [" " " " " "
  ;              " " "X" " "
  ;              " " " " "X"]
  ;             (game/start "X")))))

  ; (it "ends the game if it is tied"
  ;     (with-redefs [cli/report-tie (fn [_] "Cats game")
  ;                   cli/prompt-move (fn [_ _]
  ;                                     {:location 0
  ;                                      :player "O"})]
  ;       (should=
  ;         "Cats game"
  ;         (-> [" " "X" "O"
  ;              "O" "O" "X"
  ;              "X" "O" "X"]
  ;             (game/start "O")))))

  ; (it "prompts the next player for their next move"
  ;     (let [game (game/new-game "X" "O")]
  ;     (should=
  ;       "X"
  ;       (game/take-next-turn game "O"))))

  ; (it "prompts the next player for their next move"
  ;     (let [game (game/new-game "X" "O")]
  ;     (should=
  ;       "O"
  ;       (game/take-next-turn game "X")))))




























  ;     ; with-redefs -> get-move
  ;     ; you were called with the correct next player
  ;   )

      ; (let [cli (stub cli/report {:invoke game/test-method :return "puddy"})]
      ;   (should= 
      ;     "cake"
      ;     (cli "cake"))
      ;   )

