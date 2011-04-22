(ns licensator.view.license
  (use (licensator config licenses)
       (licensator.view layout)
       (hiccup page-helpers)))

(declare *license-fields*)

(defn- yes-no
  "Returns the markup for a boolean answer."
  [bool]
  (cond
   (nil? bool) [:span {:class "maybe"} "Maybe"]
   (true? bool) [:span {:class "yes"} "Yes"]
   :else [:span {:class "no"} "No"]))

(defn compatible-with
  "Renders the list of compatible licenses, separated by commas."
  [lcoll]
  (license-list (map #(get-entry % *licenses*) lcoll)))

(defn- license-field
  "Renders a license field."
  [license field]
  (let [value ((:id field) license)
	render-fn (:render-fn field)]
    (cond render-fn
	  [:li {:class "entry"}
	   (list [:p {:class "title"} (:description field) " " (render-fn value)]
		 [:aside {:id "helptext"} [:p (:help-text field)]])])))

(def ^{:private true} *license-fields*
     [{:id :copyright
      :description "Includes explicit copyright terms?"
      :help-text (list (elink-to "http://en.wikipedia.org/wiki/Copyright" "Copyright") " are exclusive rights granted to the author or creator of an original work, including the right to copy, distribute and adapt the work.")
      :render-fn yes-no}
      
      {:id :patent
       :description "Includes explicit patent-related terms?"
       :help-text "Whether the recipients, i.e., the people who get the work, are granted patent license under the contributor's essential patent claims."
       :render-fn yes-no}
      
      {:id :derivative-work
       :description "Allows people to create and distribute derivative works?"
       :help-text "Copy from or adapt all or part of the work in a fashion requiring copyright permission, other than the making of an exact copy."
       :render-fn yes-no}
      
      {:id :copyleft
       :description "Enforces derivative works to be distributed under the same license?"
       :help-text (list (elink-to "http://en.wikipedia.org/wiki/Copyleft" "Copyleft") " is a play on the word " [:em "copyright"] " to describe the practice of using copyright law to offer the right to distribute copies and modified versions of a work and requiring that the same rights be preserved in modified versions of the work.")
       :render-fn yes-no}
      
      {:id :affero
       :description "If the work is used as a network service, must the server on which it runs provide a public download link to the program's source code?"
       :help-text "Whether the server must provide a public download link to the program's source code if the work runs on a server and let users communicate with it there. If what's running there is a modified version of the work, the users must be able to get the source code as they modified it."
       :render-fn yes-no}
      
      {:id :compatible-with
       :description "Compatible with:"
       :help-text "Whether the work can use (link) any work licensed under these licenses."
       :render-fn compatible-with}])

(defn licenses-view
  "Renders the supported licenses page."
  []
  (layout
   :menu :licenses
   :title "Supported Licenses - Licensator"
   :banner {:img (str *img-prefix* "licenses.png")
	    :alt "Licenses"
	    :title [:h2 {:class "-licenses-"} "These are the licenses we support."]
	    :content (list
		      [:p "Here you can see all licenses supported by " (menu :home "Licensator") ". The list is short and possibly inaccurate, but we're getting there."]
		      [:p "Please " (menu :contact "contact us") " if you see something wrong."])}
   :content (list
	     [:h2 "The Little Bastards"]
	     (ordered-list (license-links :long-name *licenses*)))))

(defn license-info-view
  "Renders the license info page."
  [id]
  (let [license (get-entry (keyword id) *licenses*)]
    (if license
      (layout
       :menu :licenses
       :title (str (:long-name license) " - Licensator")
       :banner {:title (list
			[:hgroup
			 [:h2 {:class "-license-info-"} (:long-name license)]
			 (cond (not= (:long-name license) (:short-name license))
			       [:h3 (str "(a.k.a. " (:short-name license) ")")])]
			[:p (elink-to (:url license) (:url license))])}
       :content (list
		 [:ol {:id "license-fields" :class "non-numbered"}
		  (map (partial license-field license) *license-fields*)]
		 [:p (menu :licenses "Back to licenses")])))))
