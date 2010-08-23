(ns licensator.production
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use (ring.middleware params)
	(ring.util servlet)
	(licensator core middleware)))

(def app
     (-> handler
	 (wrap-append-slash)
	 (wrap-params)))

(defservice app)
