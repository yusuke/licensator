(ns licensator.view.layout
  (:use (licensator config)
	(hiccup core form-helpers page-helpers)))

(def ^{:private true} *menu*
     [{:id :home     :uri "/"          :title "Home"}
      {:id :licenses :uri "/licenses/" :title "Licenses"}
      {:id :about    :uri "/about/"    :title "About"}
      {:id :contact  :uri "/contact/"  :title "Contact"}])

(defn get-menu
  "Returns the menu entry corresponding to the given id keyword."
  [id-kw]
  (first (filter #(= (:id %) id-kw) *menu*)))

(defn menu
  "Returns the link markup for a menu entry."
  [id-kw text]
  (link-to (:uri (get-menu id-kw)) text))

(defn menu-item
  "Returns the markup for the given menu item."
  [current-id-kw menu-entry]
  [:li
   (cond (= current-id-kw (:id menu-entry))
	 {:class "active"})
   (link-to (:uri menu-entry) (:title menu-entry))])

(defn elink-to
  "Returns the markup for an external link."
  [url & content]
  [:a {:href url :rel "external"} content])

(defn include-style
  "Returns the markup that links a CSS style sheet."
  [& styles]
  (for [{:keys [href media]} styles]
    [:link {:type "text/css" :href href :rel "stylesheet" :media media}]))

(defn license-link
  "Returns the link markup for the given license."
  [content-kw license]
  [:a {:href (str "/licenses/" (name (:id license)) "/") :title (:long-name license)} (content-kw license)])

(defn js-back-link
  "Returns the link markup for a javascript-based 'back' link."
  [text]
  [:a {:href "javascript: history.back(-1);"} text])

(defn google-analytics
  "Returns the script code used to track the website traffic with Google Analytics."
  [key]
  (str "<script>var _gaq = [['_setAccount', '" key "'], ['_trackPageview']]; (function(d, t) { var g = d.createElement(t), s = d.getElementsByTagName(t)[0]; g.async = true; g.src = '//www.google-analytics.com/ga.js'; s.parentNode.insertBefore(g, s); })(document, 'script');</script>"))

(defn license-links
  "Returns a sequence of all supported licenses sorted by content-kw."
  [content-kw lcoll]
  (map (partial license-link content-kw) (sort-by content-kw lcoll)))

(defn license-list
  "Returns a list of license links separated by commas."
  [lcoll]
  (interpose ", " (license-links :short-name lcoll)))

(defn layout
  "Renders the HTML code for any page using a consistent layout."
  [& {:keys [menu description keywords title banner content]}]
  (html
   (doctype :html5)
   (xhtml-tag
    "en"
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
     [:meta {:name "description" :content "Licensator is a free service that helps you choose the right open source license for your projects."}]
     [:meta {:name "author" :content "Destaquenet Solutions"}]
     [:meta {:name "keywords" :content "pick open source license chooser app help compatibility"}]
     [:meta {:name "viewport" :content "width=device-width; initial-scale=1.0; maximum-scale=1.0;"}]
     [:title title]
     (apply include-style [{:href (str *css-prefix* "style.css") :media "all"}
			   {:href (str *css-prefix* "handheld.css") :media "handheld"}])
     (apply include-js [(str *js-prefix* "modernizr-1.5.min.js")])]
    
    [:body
     [:div {:id "container"}
      [:header {:id "banner" :class "body"}
       [:h1 [:a {:href "/"} "Licensator"]]
       [:nav
	[:ul (map (partial menu-item menu) *menu*)]]]
      
      (cond banner
	    [:aside {:id "about" :class "body"}
	     [:article
	      (cond (:img banner)
		    [:figure [:img {:src (:img banner) :alt (:alt banner)}]])
	      (:title banner)
	      (:content banner)]])
      
      (cond content
	    [:div {:id "main" :class "body"}
	     [:section {:id "content" :class "body"}
	      content]])
      
      [:footer {:id "contentinfo" :class "body"}
       [:p "Copyleft 2010-2011 &minus; " [:a {:href "http://destaquenet.com" :rel "external"} "Destaquenet Solutions"]]]

      (str "<!--[if lt IE 7 ]><script src=\"" *js-prefix* "dd_belatedpng.js" "\"></script><![endif]-->")
      (google-analytics *google-analytics-key*)]])))
