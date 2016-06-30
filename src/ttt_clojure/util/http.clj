(ns ttt-clojure.util.http)

(defn- assign-status [resp status]
  (assoc resp :status status))

(defn- assign-headers [resp headers]
  (if (:headers resp)
    (let [updated-headers (merge (:headers resp) headers)]
      (assoc resp :headers updated-headers))
    (assoc resp :headers headers)))

(defn- build-response [body status content-type]
  (-> {}
      (assign-status status)
      (assign-headers {"Content-Type" content-type})
      (assoc :body body)))

(defn basic-response [body]
  (build-response body 200 "text/html"))

(defn json-response [body]
  (build-response body 200 "application/json"))

(defn not-found [body]
  (build-response body 404 "text/html"))
