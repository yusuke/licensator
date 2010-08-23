(ns licensator.test.middleware
  (:use (licensator middleware) :reload-all)
  (:use (clojure test)))

(def ^{:private true} test-app
     (wrap-append-slash (constantly :response)))

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