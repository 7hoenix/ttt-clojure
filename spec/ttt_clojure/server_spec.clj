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
              (let [game1 (server/app (mock/request :post "/games"))]
                (should=
                  [" " " " " " " " " " " " " " " " " "]
                  (:board (get
                            (:body (server/app (mock/request :post "/games")))
                            2))))))

(describe "GET /games/1"
          (it "should display the game with id of 1"
              (let [game (server/app (mock/request :post "/games"))]
                (should=
                        [" " " " " " " " " " " " " " " " " "]
                        (:board (get
                                  (:body (server/app (mock/request :get "/games/1")))
                                  1))))))
