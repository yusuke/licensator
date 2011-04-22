(ns licensator.licenses
  (use clojure.set))

(def *keys* [:id :long-name :short-name :url :copyright :patent
	     :derivative-work :affero :copyleft :compatible-with])

(def *licenses*
     [{:id :afl-v30
       :long-name "Academic Free License v3.0"
       :short-name "AFL v3.0"
       :url "http://www.opensource.org/licenses/afl-3.0.php"
       :copyright true
       :patent true
       :derivative-work true
       :affero false
       :copyleft false
       :compatible-with [:afl-v30 :apl-v20 :bsd :freebsd :mit :new-bsd :public-domain]}
      
      {:id :apl-v20
       :long-name "Apache License v2.0"
       :short-name "Apache v2.0"
       :url "http://www.opensource.org/licenses/apache2.0.php"
       :copyright true
       :patent true
       :derivative-work true
       :affero false
       :copyleft false
       :compatible-with [:apl-v20 :afl-v30 :cddl-v10 :epl-v10 :freebsd :new-bsd :mit :mpl-v11 :public-domain]}
      
      {:id :cddl-v10
       :long-name "Common Development and Distribution License v1.0"
       :short-name "CDDL v1.0"
       :url "http://www.opensource.org/licenses/cddl1.php"
       :copyright true
       :patent true
       :derivative-work true
       :affero false
       :copyleft true
       :compatible-with [:cddl-v10 :apl-v20 :afl-v30 :epl-v10 :freebsd :mit :new-bsd :public-domain]}
      
      {:id :epl-v10
       :long-name "Eclipse Public License v1.0"
       :short-name "EPL v1.0"
       :url "http://www.opensource.org/licenses/eclipse-1.0.php"
       :copyright true
       :patent true
       :derivative-work true
       :affero false
       :copyleft false ;; Maybe
       :compatible-with [:epl-v10 :apl-v20 :cddl-v10 :freebsd :mit :new-bsd :public-domain]}
      
      {:id :freebsd
       :long-name "FreeBSD License"
       :short-name "FreeBSD"
       :url "http://www.freebsd.org/copyright/freebsd-license.html"
       :copyright true
       :patent false
       :derivative-work true
       :affero false
       :copyleft false
       :compatible-with [:freebsd :mit :new-bsd :public-domain]}
      
      {:id :agpl-v30
       :long-name "GNU Affero General Public License v3.0"
       :short-name "AGPL v3.0"
       :url "http://www.opensource.org/licenses/agpl-v3.html"
       :copyright true
       :patent true
       :derivative-work true
       :affero true
       :copyleft true
       :compatible-with [:agpl-v30 :apl-v20 :freebsd :gpl-v30 :lgpl-v30 :mit :new-bsd :public-domain]}
      
      {:id :gpl-v20
       :long-name "GNU General Public License v2.0"
       :short-name "GPL v2.0"
       :url "http://www.opensource.org/licenses/gpl-2.0.php"
       :copyright true
       :patent false
       :derivative-work true
       :affero false
       :copyleft true
       :compatible-with [:gpl-v20 :freebsd :lgpl-v21 :mit :new-bsd :public-domain]}
      
      {:id :gpl-v30
       :long-name "GNU General Public License v3.0"
       :short-name "GPL v3.0"
       :url "http://www.opensource.org/licenses/gpl-3.0.html"
       :copyright true
       :patent true
       :derivative-work true
       :affero false
       :copyleft true
       :compatible-with [:gpl-v30 :agpl-v30 :apl-v20 :freebsd :lgpl-v21 :lgpl-v30 :mit :new-bsd :public-domain]}
      
      {:id :lgpl-v21
       :long-name "GNU Library or \"Lesser\" General Public License v2.1"
       :short-name "LGPL v2.1"
       :url "http://www.opensource.org/licenses/lgpl-2.1.php"
       :copyright true
       :patent false
       :derivative-work true
       :affero false
       :copyleft true
       :compatible-with [:lgpl-v21 :freebsd :mit :new-bsd :public-domain]}
      
      {:id :lgpl-v30
       :long-name "GNU Library or \"Lesser\" General Public License v3.0"
       :short-name "LGPL v3.0"
       :url "http://www.opensource.org/licenses/lgpl-3.0.html"
       :copyright true
       :patent true
       :derivative-work true
       :affero false
       :copyleft true
       :compatible-with [:lgpl-v30 :apl-v20 :freebsd :lgpl-v21 :mit :new-bsd :public-domain]}
      
      {:id :mit
       :long-name "MIT/X11 License"
       :short-name "MIT/X11"
       :url "http://www.opensource.org/licenses/mit-license.php"
       :copyright true
       :patent false
       :derivative-work true
       :affero false
       :copyleft false
       :compatible-with [:mit :freebsd :new-bsd :public-domain]}
      
      {:id :mpl-v11
       :long-name "Mozilla Public License v1.1"
       :short-name "MPL v1.1"
       :url "http://www.opensource.org/licenses/mozilla1.1.php"
       :copyright true
       :patent true
       :derivative-work true
       :affero false
       :copyleft true
       :compatible-with [:mpl-v11 :apl-v20 :freebsd :mit :new-bsd :public-domain]}
      
      {:id :new-bsd
       :long-name "New BSD License"
       :short-name "New BSD"
       :url "http://www.opensource.org/licenses/bsd-license.php"
       :copyright true
       :patent false
       :derivative-work true
       :affero false
       :copyleft false
       :compatible-with [:new-bsd :bsd :freebsd :mit :public-domain]}
      
      {:id :bsd
       :long-name "Original BSD License"
       :short-name "BSD"
       :url "http://www.opensource.org/licenses/bsd-license.php"
       :copyright true
       :patent false
       :derivative-work true
       :affero false
       :copyleft false
       :compatible-with [:bsd :freebsd :new-bsd :mit :public-domain]}
      
      {:id :public-domain
       :long-name "Public Domain"
       :short-name "Public Domain"
       :url "http://en.wikipedia.org/wiki/Public_domain"
       :copyright false
       :patent false
       :derivative-work true
       :affero false
       :copyleft false
       :compatible-with [:public-domain]}])

(defn get-entry
  "Returns the entry corresponding to the given id."
  [id lcoll]
  (first (filter #(= (:id %) id) lcoll)))

(defn where
  "Returns a criteria fn that matches all given attributes."
  [cmap]
  (fn [lentry]
    (let [res (map (fn [centry]
		     (let [cval (val centry)
			   lval ((key centry) lentry)]
		       (if (nil? cval)
			 true
			 (if (coll? lval)
			   (subset? (set cval) (set lval))
			   (= cval lval))))) cmap)]
      (every? true? res))))

(defn find-matches
  "Finds all licenses that match the given criteria map."
  [cmap data]
  (filter (where cmap) data))
