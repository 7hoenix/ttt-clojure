(ns ttt-clojure.cli
  (:require [ttt-clojure.board :as board]
            [clojure.string :as string]))

(defn get-input [question]
  (println question)
  (string/trim (string/lower-case (read-line))))

(defn new-game []
  (let [message "Would you like to start a new game? (enter y or n)"
        response (get-input message)]
    (if (= response "y")
      true
      false)))
    ; (cond
    ;   (= response "y") true
    ;   (= response "n") false
    ;   :else (new-game))))

