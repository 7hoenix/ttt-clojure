(ns ttt-clojure.cli)

(defn get-input [question]
  (println question)
  (read-line))

(defn new-game []
  (let [message "Would you like to start a new game? (enter y or n)"
        response (get-input message)]
    (if (= response "y")
      true
      false)))
