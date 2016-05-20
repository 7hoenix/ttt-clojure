(ns ttt-clojure.ui
  (:require [ttt-clojure.cli :as cli]
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

(defn new-game ([] (new-game println read-line))
  ([disp-func prompt-func]
   (let [message "Would you like to start a new game? (enter y or n)"
         response (prompt disp-func prompt-func message)]
     (cond
       (= response "y") true
       (= response "n") false
       :else (new-game disp-func prompt-func)))))

(defn- clear-screen []
  (print (str (char 27) "[2J"))
  (print (str (char 27) "[;H")))

(defn- prepare-row [row]
  (apply str (interpose " | " row)))

(defn- prepare-board [board]
  (let [rows (partition 3 board)
        top (prepare-row (first rows))
        middle (prepare-row (second rows))
        bottom (prepare-row (last rows))
        blank (apply str (repeat 9 "-"))
        together (string/join "\n" [top blank middle blank bottom])]
    together))

(defn print-board [disp-func board]
  (clear-screen)
  (let [board-with-indexes (map-indexed (fn [idx item]
                                          (if (= item " ")
                                            idx
                                            item))
                                        board)
        printable (prepare-board board-with-indexes)]
    (report disp-func printable)))


(defn- str->int [s]
  (Integer. s))

(defn- response-is-available? [available response]
  (some #(= response %) available))

(defn prompt-move ([] (prompt-move println read-line))
  ([available current-player] (prompt-move println
                                     read-line
                                     available
                                     current-player))
  ([disp-func prompt-func available current-player]
   (let [message (str current-player " your available moves are " available)
         response (str->int (prompt disp-func prompt-func message))]
    (if (response-is-available? available response)
          {:location response :player current-player}
          (prompt-move disp-func
                       prompt-func
                       available
                       current-player)))))
