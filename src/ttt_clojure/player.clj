(ns ttt-clojure.player)

(defprotocol PPlayer
  (display [this message])
  (take-turn [this board]))
