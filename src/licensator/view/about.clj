(ns licensator.view.about
  (use (licensator config)
       (licensator.view layout)))

(defn about-view
  "Renders the about page."
  []
  (layout
   :title "About - Licensator"
   :menu :about
   :banner {:img (str *img-prefix* "devil.png")
	    :alt "About us"
	    :title [:h2 {:class "-about-"} "Why?"]
	    :content [:div
		      [:p "If you do some research, you'll find that there are " (elink-to "http://opensource.org/licenses/" "literally hundreds") " of open source licenses. Most of them are not used anymore, or are superseded by other licenses, or are incompatible with other licenses."]
		      [:p "As you can see, things can get ugly. That's why we wrote this app."]]}
   :content [:div
	     [:h2 "Who?"]
	     [:p (menu :home "Licensator") " is developed by " (elink-to "http://www.destaquenet.com/company/#daniel" "Daniel Martins") " and sponsored by " (elink-to "http://destaquenet.com" "Destaquenet") ", a brazilian company that offers high-quality mentoring and software development services based on free and open source technologies."]
	     [:h2 "Tech?"]
	     [:p (menu :home "Licensator") " is written in " (elink-to "http://clojure.org" "Clojure 1.2") ", a dynamic programming language that targets the " (elink-to "http://java.com" "Java VM") ". We also used " (elink-to "http://github.com/weavejester/compojure" "Compojure") ", " (elink-to "http://github.com/weavejester/hiccup" "Hiccup") ", " (elink-to "http://github.com/technomancy/leiningen" "Leiningen") ", " (elink-to "http://html5boilerplate.com" "HTML5 Boilerplate") ", and " (elink-to "http://tango.freedesktop.org" "Tango Desktop Project") ". No database, baby!"]
	     [:p "If you are interested to see how the app works, or even deploy it on your own server, feel free to " (elink-to "http://github.com/danielfm/licensator" "download a copy") "."]
	     [:h2 "Disclaimer"]
	     [:p "This Website and the information, names, images, pictures, logo's and icons regarding or relating to Licensator is provided \"as is\" and on an \"as available\" basis without any representation or endorsement made and without warranty of any kind, whether expressed or implied, including, but not limited to the implied warranties or merchantability, fitness for a particular purpose or non-infringement."]
	     [:p "In no event shall Licensator be liable for any damages, including without limitation, special, indirect or consequential damages, or any damages, whatsoever resulting from access or use, or inability to access or use this Website or arising out of any materials, information, qualifications or recommendations on this Website."]]))