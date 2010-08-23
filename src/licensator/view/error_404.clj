(ns licensator.view.error-404
  (use (licensator config)
       (licensator.view layout)))

(defn not-found-view
  "Renders the 404 error page."
  []
  (layout
   :menu :home
   :title "Page not found - Licensator"
   :banner {:img (str *img-prefix* "404.png")
	    :alt "Page not found"
	    :title [:h2 "Sorry, that page doesn't exist!"]
	    :content (list [:p "You must be feeling " [:strong "awesome"] ", aren't you?"]
			   [:p "Anyways, use that menu bar on the top to find what you want. If you think you found a bug or something, please " (menu :contact "let us know") "."])}))