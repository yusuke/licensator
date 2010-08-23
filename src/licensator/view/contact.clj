(ns licensator.view.contact
  (use (licensator config)
       (licensator.view layout)))

(defn contact-view
  "Renders the contact page."
  []
  (layout
   :menu :contact
   :title "Contact - Licensator"
   :banner {:img (str *img-prefix* "contact.png")
	    :alt "Contact"
	    :title [:h2 {:class "-contact-"} "We'd love hear from you!"]
	    :content (list
		      [:p "This is a fairly new app, so weird things can happen despite our best efforts."]
		      [:p "If you found a bug, or want to send us suggestions, critics, or just words of support, please " [:a {:href (str "mailto:" *contact-email*)} "drop us an e-mail"] "."])}))