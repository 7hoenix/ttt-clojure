(ns ttt-clojure.util.http)

(defn- assign-status [resp status]
  (assoc resp :status status))

(defn- assign-headers [resp headers]
  (if (:headers resp)
    (let [updated-headers (merge (:headers resp) headers)]
      (assoc resp :headers updated-headers))
    (assoc resp :headers headers)))

(defn- build-response [body status]
  (-> {}
      (assign-status status)
      (assign-headers {"Content-Type" "text/html"})
      (assoc :body body)))

(defn basic-response [body]
  (build-response body 200))

(defn not-found [body]
  (build-response body 404))

(defn redirect [body id]
  (assign-headers
    (build-response body 302)
    {"Location" (str "/games/" id)}))
