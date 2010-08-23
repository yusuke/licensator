(ns licensator.view.index
  (use (licensator config licenses)
       (licensator.view layout)
       (hiccup form-helpers page-helpers)))

(declare *license-fields*)

(defn- to-boolean
  "Converts a string to a boolean. If str is nil or empty, returns nil."
  [^String str]
  (if (empty? str)
    nil
    (Boolean/valueOf str)))

(defn- compatible-list
  "Returns a list of keywords that represents the compatible licenses of a
given license."
  [coll]
  (if-not (coll? coll)
    (list (keyword coll))
    (map keyword (filter (complement empty?) coll))))

(defn- yes-no
  "Renders form controls for a 'yes-no-dunno' kind of answer."
  [question-id]
  (let [group (name question-id)
	options [{:text "Yes" :value "true"}
		 {:text "No" :value "false"}
		 {:text "I don't know/care" :value ""}]]
    (for [{:keys [text checked value]} options]
      [:div {:class "choice"}
       (radio-button group nil value)
       (label (str group "-" value) text)])))

(defn- license-check
  "Renders a checkbox input field for each supported license."
  [question-id]
  (let [group (name question-id)]
    (for [{:keys [id long-name short-name]} (sort-by :short-name *licenses*)]
      (let [input-id (str group "-" (name id))]
	[:div {:class "choice"}
	 [:input {:type "checkbox" :name group :id input-id :value id}]
	 [:label {:for input-id :title long-name} short-name]]))))

(defn- render-question
  "Renders a question. See *questions* var for more details."
  [{:keys [id question help-text render-fn]}]
  [:li {:class "entry"}
   (list [:p {:class "title"} question]
	 [:div {:class "choices"} (render-fn id)]
	 [:aside {:class "helptext"} [:p help-text]])])

(def ^{:private true} *license-fields*
     [{:id :compatible-with
       :question "If your work is based on other open source projects, which are these project's licenses?"
       :help-text "\"Based\" means to copy from or adapt all or part of another work in a fashion requiring copyright permission, other than the making of an exact copy."
       :render-fn license-check
       :parse-fn compatible-list}
      
      {:id :copyright
       :question "Do you want the license to include explicit copyright terms?"
       :help-text (list (elink-to "http://en.wikipedia.org/wiki/Copyright" "Copyright") " are exclusive rights granted to the author or creator of an original work, including the right to copy, distribute and adapt the work.")
       :render-fn yes-no
       :parse-fn to-boolean}
      
      {:id :patent
       :question "Do you want the license to include explicit patent-related terms?"
       :help-text "Check \"Yes\" if the recipients, i.e., the people who get the work, are granted patent license under the contributor's essential patent claims."
       :render-fn yes-no
       :parse-fn to-boolean}
      
      {:id :closed-source-linking
       :question "Do you allow you work to be used by closed-source software?"
       :help-text "Check \"Yes\" if you permit closed-source software to use or include a copy or a modified version of your work."
       :render-fn yes-no
       :parse-fn to-boolean}
      
      {:id :affero
       :question "If your work is used as a network service, must the server on which it runs provide a pubilc download link to the program's source code?"
       :help-text "If the work runs on a server and let users communicate with it there, check \"Yes\" if the server must allow the users to download the source code corresponding to the program that it's running. If what's running there is a modified version of the work, the users must be able to get the source code as they modified it."
       :render-fn yes-no
       :parse-fn to-boolean}
      
      {:id :copyleft
       :question "Do you want derivative works to be distributed under the same license?"
       :help-text (list (elink-to "http://en.wikipedia.org/wiki/Copyleft" "Copyleft") " is a play on the word " [:em "copyright"] " to describe the practice of using copyright law to offer the right to distribute copies and modified versions of a work and requiring that the same rights be preserved in modified versions of the work.")
       :render-fn yes-no
       :parse-fn to-boolean}
      
      {:id :charge-for-use
       :question "Can people distribute your work and charge its use?"
       :help-text (list "Check \"Yes\" if you allow people to sell your work " [:em "and"] " charge for its use.")
       :render-fn yes-no
       :parse-fn to-boolean}])

(defn index-view
  "Renders the index page."
  []
  (layout
   :menu :home
   :title "Licensator - The open source license adviser"
   :banner {:img (str *img-prefix* "find.png")
	    :alt "Find the right license"
	    :title [:hgroup
		    [:h2 {:class "-home-"} "Starting a new open source project?"]
		    [:h3 (menu :about "We can help you!")]]
	    :content (list [:p "Create your own open source project can be a lot of fun! Except, of course, when you have to decide " (menu :licenses "which license") " to use."]
			   [:p (menu :home "Licensator") " is a free service that helps you with this boring task. All you have to do is answer a few questions about your project and we'll recommend the licenses that fit best. "]
			   [:p "Enjoy!"])}
   :content (list [:h2 "Answer as many questions as you can and hit \"Go!\""]
		  (form-to [:post "/"]
			   [:ol {:id "license-fields" :class "numbered"}
			    (map render-question  *license-fields*)]
			   [:div {:class "commands"}
			    (submit-button "Go!")]))))

(defn- parse-param
  ""
  [[k v]]
  (let [key (keyword k)
	cfn (:parse-fn (get-entry key *license-fields*))
	val (if cfn (cfn v) v)]
    [key val]))

(defn- parse-form
  ""
  [form]
  (apply hash-map (mapcat parse-param form)))

(defn- licenses-count-msg
  [lcoll]
  (let [c (count lcoll)]
    (cond
     (zero? c) [:p "You'd probably want to " (js-back-link "go back") " and review some of your choices. If that doesn't work either, you should consider release your work under " (elink-to "http://en.wikipedia.org/wiki/Dual_license" "multiple licenses") "."]
     (= c 1) [:p "Apparently we found the perfect match for your situation! Nevertheless, you should read the license terms very carefully just to be sure."]
     (< c 4) [:p "This is a good number! Now, instead of picking one at random, you should read them all very carefully and pick the right one."]
     :else [:p "This is still a lot! You'd probably want to " (js-back-link "go back") " and review some of your choices."])))

(defn licenses-found
  "Renders the page when one or more compatible licenses is found."
  [lcoll]
  (layout
   :menu :home
   :title "Results - Licensator"
   :banner {:img (str *img-prefix* "found.png")
	    :alt "Compatible licenses"
	    :title [:hgroup
		    [:h2 {:class "-results-"} "Yay! There are compatible licenses!"]]
	    :content (list [:p "We found " [:strong {:class "big"} (count lcoll)] " license(s) that you can use to license your work."]
			   (licenses-count-msg lcoll))}
   :content (list [:h2 "Recommended License(s)"]
		  (ordered-list (license-links :long-name lcoll))
		  [:p "Found something wrong? Please " (menu :contact "let us know") "."])))

(defn licenses-not-found
  "Renders the page when no compatible license is found."
  [lcoll]
  (layout
   :menu :home
   :title "Results - Licensator"
   :banner {:img (str *img-prefix* "not-found.png")
	    :alt "No licenses found"
	    :title [:h2 {:class "-results-"} "No compatible licenses. Dammit!"]
	    :content (list [:p "We're sorry, but " [:strong "none"] " of the licenses match your answers."]
			   (licenses-count-msg lcoll))}))

(defn suggest-view
  "Renders the license suggestions page."
  [form]
  (let [criteria (parse-form form)
	lcoll (find-matches criteria *licenses*)]
    ((if (empty? lcoll)
       licenses-not-found
       licenses-found) lcoll)))
