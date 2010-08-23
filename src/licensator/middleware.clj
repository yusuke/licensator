(ns licensator.middleware
  (:use (ring.util codec response)))

(defn wrap-append-slash
  "Handles the request if the request path ends with a slash, or returns a
redirect to the same path with a trailing slash."
  [handler]
  (fn [request]
    (if-not (= :get (:request-method request))
      (handler request)
      (let [^String path (url-decode (:uri request))]
	(if (.endsWith path "/")
	  (handler request)
	  (redirect (str path "/")))))))