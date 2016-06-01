(ns ttt-clojure.core
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.players.cli :as cli]
            [ttt-clojure.players.ai :as ai])
  (:gen-class))

(defn -main []
  (cli/new-game)
    (let [player1 (cli/create-cli "X" "O")
          player2 (ai/create-ai "O" "X")
          game (game/create-new-game player1 player2)]
    (game/start game)))
