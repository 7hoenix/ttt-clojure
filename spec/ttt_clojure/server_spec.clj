(ns ttt-clojure.server-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.server :as server]
            [ttt-clojure.game :as game]
            [ttt-clojure.players.web :as web]
            [ttt-clojure.players.ai :as ai]
            [clojure.string :as string]
            [ring.mock.request :as mock]))

(defn reset-games []
  (reset! server/games {}))

(describe "GET /new-game"
          (it "displays a message to start a new game"
              (reset-games)
              (should=
                true
                (string/includes?
                  (:body (server/app (mock/request :get "/")))
                  "start a new game?"))))

(describe "POST /games"
          (it "returns a newly created game"
              (reset-games)
              (let [game1 (server/app (mock/request :post "/games"))]
                (should=
                  [" " " " " " " " " " " " " " " " " "]
                  (:board (get
                            (:body (server/app (mock/request :post "/games")))
                            2))))))

(describe "GET /games/1"
          (it "should display the game with id of 1"
              (reset-games)
              (let [game (server/app (mock/request :post "/games"))]
                (should=
                        [" " " " " " " " " " " " " " " " " "]
                        (:board (get
                                  (:body (server/app (mock/request :get "/games/1")))
                                  1))))))

(describe "PUT /games/1?symbol=X&spot=0"
          (it "should return a board for game 1 with a move shown"
              (reset-games)
              (let [game (server/app (mock/request :post "/games"))]
                (server/app (mock/request :put "/games/1?symbol=X&spot=0"))
                (should=
                        ["X" " " " " " " " " " " " " " " " "]
                        (:board (get
                                  (:body (server/app (mock/request :get "/games/1")))
                                  1))))))
