(ns ttt-clojure.server
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.players.ai :as ai]
            [ttt-clojure.players.web :as web]))

(def create-players
  {:player1 (web/create-web "X" "O")
   :player2 (ai/create-ai "O" "X")})

(defn respond-to-get [uri & more]
  {:body "start a new game?"})

(defn respond-to-post [uri & more]
  (let [players create-players]
    {:body (game/create-new-game (:player1 players) (:player2 players))}))

(defn handler [request]
  (condp = (:request-method request)
    :get (respond-to-get (:uri request))
    :post (respond-to-post (:uri request))))

(def app
  (-> handler))
