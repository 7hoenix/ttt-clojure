(ns ttt-clojure.cli
  (:require [ttt-clojure.player :as player]
            [ttt-clojure.board :as board]
            [clojure.string :as string]))

(defn new-game ([] new-game println read-line)
  ([print-func prompt-func]
   (println "here")
   (print-func "Would you like to start a new game? (enter y or n)")
   (let [response (prompt-func)]
     (cond
       (= response "y") true
       (= response "n") false
       :else (new-game print-func prompt-func)))))

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

(defn print-board [board print-func]
  (clear-screen)
  (let [board-with-indexes (map-indexed (fn [idx item]
                                          (if (= item " ")
                                            idx
                                            item))
                                        board)
        printable (prepare-board board-with-indexes)]
    (print-func printable)
    printable))

(defn- str->int [s]
  (Integer. s))

(defn- prompt-move [message print-func prompt-func]
  (print-func message)
  (str->int (prompt-func)))

(defn- response-is-available? [available response]
  (some #(= response %) available))

(defn turn-cycle [board mark opponent-mark print-func prompt-func]
  (print-board board print-func)
  (let [available (board/available-spaces board)
        message (str mark " your available moves are: " available)
        response (prompt-move message print-func prompt-func)]
    (if (response-is-available? available response)
      {:location response :player mark}
      (turn-cycle board mark opponent-mark print-func prompt-func))))

(defrecord CLI [mark opponent-mark print-func prompt-func]
  player/PPlayer
  (display [this message] (print-func message))
  (take-turn [this board] (turn-cycle board mark opponent-mark print-func prompt-func)))

(defn create-cli ([mark opponent-mark] (create-cli mark
                                                   opponent-mark
                                                   println
                                                   read-line))
  ([mark opponent-mark print-func prompt-func]
    (->CLI mark opponent-mark print-func prompt-func)))
