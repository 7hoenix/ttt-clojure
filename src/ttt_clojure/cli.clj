(ns ttt-clojure.cli
  (:require [ttt-clojure.board :as board]
            [clojure.string :as string]))

(defn report [disp-func message]
  (disp-func message)
  message)

(defn prompt [disp-func prompt-func question]
  (report disp-func question)
  (string/trim (string/lower-case (prompt-func))))

(defn report-winner [disp-func input]
  (report disp-func
          (str input " wins")))

(defn report-tie [disp-func _]
  (report disp-func (str "Cats game")))

(defn print-board [disp-func board]
  (let [board-with-indexes (map-indexed (fn [idx item]
      (if (= item " ")
        idx
        item)) board)
        printable (apply str (apply concat
                         (interpose ["\n"]
                                    (partition 3 board-with-indexes))))]
    (report disp-func printable)))

(defn new-game [disp-func prompt-func]
  (let [message "Would you like to start a new game? (enter y or n)"
        response (prompt disp-func prompt-func message)]
    (cond
      (or (= response "y") (= response "yes")) true
      (or (= response "n") (= response "no")) false
      :else (new-game disp-func prompt-func))))

(defn- str->int [s]
  (Integer. s))

(defn- response-is-available? [available response]
  (some #(= response %) available))

(defn prompt-move [disp-func
                   prompt-func
                   available-moves
                   current-player]
  (let [message (str current-player " your available moves are " available-moves)
       response (str->int (prompt disp-func prompt-func message))]
    (if (response-is-available? available-moves response)
          {:location response :player current-player}
          (prompt-move disp-func
                   prompt-func
                   available-moves
                   current-player))))
