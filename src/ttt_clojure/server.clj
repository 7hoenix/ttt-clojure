(ns ttt-clojure.server
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.players.ai :as ai]
            [ttt-clojure.players.web :as web]))

(def create-players
  {:player1 (web/create-web "X" "O")
   :player2 (ai/create-ai "O" "X")})

(def games
  (atom {}))

(defn next-id []
  (let [index (or (:current-index @games) 0)
        id (inc index)]
    (swap! games assoc
           :current-index
           id)
    id))

(defn create-game []
  (let [players create-players
        game (swap! games assoc
                    (next-id)
                    (game/create-new-game
                      (:player1 players)
                      (:player2 players)))]
    game))

(defn respond-to-get [uri & more]
  {:body "start a new game?"})

(defn respond-to-post [uri & more]
  {:body (create-game)})

(defn handler [request]
  (condp = (:request-method request)
    :get (respond-to-get (:uri request))
    :post (respond-to-post (:uri request))))

(def app
  (-> handler))
