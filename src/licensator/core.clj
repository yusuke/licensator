(ns licensator.core
  (:use (compojure core)
	(ring.middleware reload stacktrace file file-info params)
	(ring.util codec response)
	(licensator.view index license about contact error-404))
  (:require [compojure.route :as route]))

(defroutes handler
  (GET "/" [] (index-view))
  (POST "/" {form :params} (suggest-view form))
  (GET "/licenses/:id/" [id] (license-info-view id))
  (GET "/licenses/" [] (licenses-view))
  (GET "/about/" [] (about-view))
  (GET "/contact/" [] (contact-view))
  (route/files "public")
  (route/not-found (not-found-view)))

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

(def app
     (-> handler
	 (wrap-params)
	 (wrap-file "public")
	 (wrap-file-info)
	 (wrap-append-slash)
	 (wrap-reload '[licensator.core
			licensator.config
			licensator.licenses
			licensator.view.layout
			licensator.view.index
			licensator.view.license
			licensator.view.about
			licensator.view.contact
			licensator.view.error-404])
	 (wrap-stacktrace)))
