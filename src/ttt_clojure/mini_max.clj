(ns ttt-clojure.mini-max
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.board :as board]))

(defn calculate [game player]
  (let [board (:board game)
        available (board/available-spaces board)
        next (first available)]
    next))
