(ns licensator.run
  (:use (ring.adapter jetty))
  (:require (licensator core)))

(defn start-server [port]
  (run-jetty #'licensator.core/app {:port port}))

(defn -main [& [port]]
  (if port
    (start-server (Integer/parseInt port))
    (start-server 8080)))