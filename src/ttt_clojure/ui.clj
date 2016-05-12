(ns ttt-clojure.ui
  (:require [ttt-clojure.cli :as cli]))

(defn report-winner [disp-func winner]
  (cli/report-winner disp-func winner))

(defn prompt-move [disp-func
                   prompt-func
                   available-moves
                   current-player]
  (cli/prompt-move disp-func
                   prompt-func
                   available-moves
                   current-player))

(defn print-board [disp-func board]
  (cli/print-board disp-func board))

(defn report-tie [disp-func winner]
  (cli/report-tie disp-func winner))
