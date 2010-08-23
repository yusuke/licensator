(ns licensator.local
  (:use (ring.middleware reload stacktrace file file-info params)
	(ring.adapter jetty)
	(licensator core middleware)))

(def app
     (-> handler
	 (wrap-append-slash)
	 (wrap-params)
	 (wrap-file "war")
	 (wrap-file-info)
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

(defn start-server [port]
  (run-jetty app {:port port}))

(defn -main [& [port]]
  (if port
    (start-server (Integer/parseInt port))
    (start-server 8080)))
