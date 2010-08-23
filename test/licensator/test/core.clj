(ns licensator.test.core
  (:use (licensator core) :reload-all)
  (:use (clojure test)))

(def ^{:private true} test-app
     (wrap-append-slash (constantly :response)))

(defn- req [method uri & params]
  "Fires a request to the application."
  (app {:request-method method :uri uri :params (first params)}))

(defn- assert-page
  "Fires a request to the application and verifies the response."
  [method uri & {:keys [status-code chunk params]}]
  (let [{:keys [status body]} (req method uri params)]
    (is (= (if status-code
	     status-code
	     200) status))
    (cond chunk
	  (is (re-find chunk body)))))

(deftest middleware-wrap-append-slash
  (testing "GET path with trailing slash"
    (let [result (test-app {:request-method :get :uri "/resource/"})]
      (is (= :response result))))

  (testing "GET path without trailing slash"
    (let [{:keys [status headers]} (test-app {:request-method :get :uri "/resource"})]
    (is (= 302 status))
    (is (= {"Location" "/resource/"} headers))))

  (testing "Non-GET path without trailing slash"
    (let [result (test-app {:request-method :post :uri "/resource"})]
      (is (= :response result)))))

(deftest routes
  (testing "GET non-existent page"
    (assert-page :get "/404/" :status-code 404))
  
  (testing "GET /"
    (assert-page :get "/" :chunk #"class=\"-home-\""))

  (testing "POST /"
    (assert-page :post "/" :chunk #"class=\"-results-\""))
  
  (testing "GET /about/"
    (assert-page :get "/about/" :chunk #"class=\"-about-\""))

  (testing "GET /contact/"
    (assert-page :get "/contact/" :chunk #"class=\"-contact-\""))

  (testing "GET /licenses/"
    (assert-page :get "/licenses/" :chunk #"class=\"-licenses-\""))
  
  (testing "GET valid /licenses/:id/"
    (assert-page :get "/licenses/bsd/" :chunk #"class=\"-license-info-\""))
  
  (testing "GET invalid /licenses/:id/"
    (assert-page :get "/licenses/404/" :status-code 404)))