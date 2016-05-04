(ns ttt-clojure.cli
  (:require [ttt-clojure.board :as board]
            [clojure.string :as string]))

(defn report [message]
  (println message)
  message)

(defn get-input [question]
  (report question)
  (string/trim (string/lower-case (read-line))))

(defn report-winner [input]
  (report (str input " wins")))

(defn new-game []
  (let [message "Would you like to start a new game? (enter y or n)"
        response (get-input message)]
    (cond
      (or (= response "y") (= response "yes")) true
      (or (= response "n") (= response "no")) false
      :else (new-game))))

(defn- str->int [s]
  (read-string s))

(defn prompt-move [available-moves]
  (let [message (str "Available moves are " available-moves)
       response (str->int (get-input message))]
    (cond
      (contains? available-moves response) response
      (integer? response) false
      :else (prompt-move available-moves))))
