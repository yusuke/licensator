(ns licensator.core
  (:use (compojure core)
	(licensator.view index license about contact error-404))
  (:require [compojure.route :as route]))

(defroutes handler
  (GET "/" [] (index-view))
  (POST "/" {form :params} (suggest-view form))
  (GET "/licenses/:id/" [id] (license-info-view id))
  (GET "/licenses/" [] (licenses-view))
  (GET "/about/" [] (about-view))
  (GET "/contact/" [] (contact-view))
  (route/not-found (not-found-view)))
