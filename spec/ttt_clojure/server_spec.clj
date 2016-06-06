(ns ttt-clojure.server-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.server :as server]
            [ttt-clojure.game :as game]
            [ttt-clojure.players.web :as web]
            [ttt-clojure.players.ai :as ai]
            [clojure.string :as string]
            [ring.mock.request :as mock]))

(describe "GET /new-game"
          (around [it]
                  (with-out-str (it)))

          (it "displays a message to start a new game"
              (should=
                true
                (string/includes?
                  (:body (server/app (mock/request :get "/")))
                  "start a new game?"))))

(describe "POST /games"
          (it "returns a newly created game"
              (let [game1 (server/app (mock/request :post "/games"))]
                (should=
                  200
                  (:status (server/app (mock/request :post "/games")))))))

(describe "GET /games/1"
          (it "should display the game with id of 1"
              (let [game (server/app (mock/request :post "/games"))]
                (should=
                  200
                  (:status (server/app (mock/request :get "/games/1")))))))

(describe "PUT /games/1?symbol=X&spot=0"
          (it "should return a board for game 1 with a move shown"
              (let [game (server/app (mock/request :post "/games"))]
                (server/app (mock/request :put "/games/1?player=X&location=0"))
                (should=
                  200
                  (:status (server/app (mock/request :get "/games/1")))))))
