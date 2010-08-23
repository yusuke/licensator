(ns licensator.config)

;; Media URL prefix
(def *media-prefix* "/media/")

;; Prefix for the most used kinds of resources
(def *img-prefix* (str *media-prefix* "img/"))
(def *css-prefix* (str *media-prefix* "css/"))
(def *js-prefix* (str *media-prefix* "js/"))

;; Other config
(def *contact-email* "contato@destaquenet.com")
(def *google-analytics-key* "UA-761701-8")
