(ns ttt-clojure.server-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.server :as server]
            [ttt-clojure.game :as game]
            [ttt-clojure.players.web :as web]
            [ttt-clojure.players.ai :as ai]
            [clojure.string :as string]
            [ring.mock.request :as mock]))

(describe "GET /new-game"
          (it "displays a message to start a new game"
              (should=
                true
                (string/includes?
                  (:body (server/app (mock/request :get "/")))
                  "start a new game?"))))

(describe "POST /games"
          (it "returns a newly created game"
              (let [board [" " " " " " " " " " " " " " " " " "]
                    player1 (web/create-web "X" "O")
                    player2 (ai/create-ai "O" "X")]
                (should=
                        board
                        (:board (:body (server/app (mock/request :post "/games"))))))))

; (describe "GET games/1"
;           (it "should display the game with id of 1"
;               (let [board [" " " " " " " " " " " " " " " " " "]
;                     player1 (web/create-web "X" "O")
;                     player2 (ai/create-ai "O" "X")
;                     game (game/create-new-game player1 player2)]
;                 (should=
;                         board
;                         (:board (:body (server/app (mock/request :get "games/1"))))))))
