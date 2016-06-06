(ns ttt-clojure.game-storage
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.players.ai :as ai]
            [ttt-clojure.players.web :as web]))

(def games
  (atom {}))

(defn clear-db []
  (reset! games {}))

(defn str->int [s]
  (Integer. s))

(def create-players
  {:player1 (web/create-web "X" "O")
   :player2 (ai/create-ai "O" "X")})

(defn next-id []
  (let [index (or (:current-index @games) 0)
        id (inc index)]
    (swap! games assoc
           :current-index
           id)
    id))

(defn create-game []
  (let [players create-players
        id (next-id)
        games (swap! games assoc
                    id
                    (game/create-new-game
                      (:player1 players)
                      (:player2 players)))]
    (get games id)))

(defn game [id]
  (get (deref games) id))

(defn update-game [id updated-game]
    (swap! games assoc
           id
           updated-game))

(defn make-move [id move]
  (let [game (game id)
        updated-game (game/make-move game move)]
    (update-game id updated-game)
    updated-game))
