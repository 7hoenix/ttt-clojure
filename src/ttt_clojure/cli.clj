(ns ttt-clojure.cli)

(defn get-input [question]
  (println question)
  (read-line))
