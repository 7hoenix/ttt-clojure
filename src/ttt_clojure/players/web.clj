(ns ttt-clojure.players.web
  (:require [ttt-clojure.player :as player]))

(defrecord Web [mark opponent]
  player/PPlayer
  (display [this message] message)
  (take-turn [this board] board))

(defn create-web [mark opponent]
  (->Web mark opponent))
