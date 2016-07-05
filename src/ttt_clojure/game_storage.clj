(ns ttt-clojure.game-storage
  (:require [ttt-clojure.basic-game :as basic-game]
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
  {:current-player (web/create-web "X" "O")
   :other-player (ai/create-ai "O" "X")})

(defn- next-id [games]
  (let [index (or (:current-index @games) 0)
        id (inc index)]
      (set-current-index! games id)))

(defn- switch-players [game]
  (let [new-current-player (:other-player game)]
    (assoc game :other-player (:current-player game)
                :current-player new-current-player)))

(defprotocol GameRepository
  (games [this])
  (create-game [this])
  (show-game [this id])
  (update-game [this id game])
  (make-move [this id move]))

(defrecord AtomGameRepository [games]
  GameRepository
  (games [this] (deref games))

  (create-game [this]
    (let [players create-players
          id (next-id games)
          new-game (basic-game/create-new-game (:current-player players)
                                               (:other-player players))
          updated-game (store-game-by-id! games id new-game)]
      {:id id
       :game updated-game}))

  (show-game [this id]
    {:id id
     :game (get (deref games) id)})

  (update-game [this id game-info]
    {:id id
     :game (store-game-by-id! games id (:game game-info))})

  (make-move [this id move]
    (let [game (get (deref games) id)
          updated-game (basic-game/make-move game move)
          updated-game-with-swapped-players (switch-players updated-game)]
      {:id id
       :game (store-game-by-id! games id updated-game-with-swapped-players)})))

(defn create-atom-game-repo []
  (map->AtomGameRepository {:games (atom {})}))
