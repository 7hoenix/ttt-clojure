(ns ttt-clojure.core
  (:require [ttt-clojure.game :as game]
            [ttt-clojure.ui :as ui])
  (:gen-class))

(defn -main []
  (if (ui/new-game)
    (game/start)))
