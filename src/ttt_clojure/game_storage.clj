(ns ttt-clojure.game-storage
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.board :as board]
            [ttt-clojure.players.ai :as ai]
            [ttt-clojure.players.web :as web]))

(defn- str->int [s]
  (Integer. s))

(defn- set-current-index! [games id]
  (get (swap! games assoc :current-index id)
       :current-index))

(defn- store-game-by-id! [games id game]
  (get (swap! games assoc id game)
       id))

(def ^:private create-players
  {:player1 (web/create-web "X" "O")
   :player2 (ai/create-ai "O" "X")})

(defn- next-id [games]
  (let [index (or (:current-index @games) 0)
        id (inc index)]
      (set-current-index! games id)))

(defprotocol GameRepository
  (games [this])
  (create-game [this])
  (show-game [this id])
  (make-move [this id move]))

(defrecord AtomGameRepository [games]
  GameRepository
  (games [this] (deref games))

  (create-game [this]
    (let [players create-players
          id (next-id games)
          new-game (game/create-new-game (:player1 players)
                                         (:player2 players))
          updated-game (store-game-by-id! games id new-game)]
      {:id id
       :game updated-game}))

  (show-game [this id]
    (get (deref games) id))

  (make-move [this id move]
    (let [game (show-game this id)
          updated-game (game/make-move game move)]
      (store-game-by-id! games id updated-game))))

(defn create-atom-game-repo []
  (map->AtomGameRepository {:games (atom {})}))
