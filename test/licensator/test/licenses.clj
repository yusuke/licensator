(ns licensator.test.licenses
  (:use (licensator licenses) :reload-all)
  (:use (clojure test)))

(deftest licenses-keys
  (testing "All licenses must have the same set of keys"
    (letfn [(check-keys [license]
	      (= (sort *keys*) (sort (keys license))))]
      (is (every? true? (map check-keys *licenses*))))))

(deftest fn-get-entry
  (testing "Valid key"
    (let [license (get-entry :bsd *licenses*)]
      (is (= :bsd (:id license)))
      (is (= "BSD" (:short-name license)))))

  (testing "Invalid key"
    (is (nil? (get-entry :anything-else *licenses*)))))

(deftest fn-where
  (testing "true/false query"
    (let [criteria-fn (where {:a true, :b false})
	  data [{:a false, :b false}
		{:a true, :b false, :c true}
		{:a true, :b true}]]
      
      (is (= '(false true false) (map criteria-fn data)))))

  (testing "subset inclusion query"
    (let [criteria-fn (where {:a [:x :y :z], :b [:x :y]})
	  data [{:a [:x :y :z], :b [:x :y]}
		{:a [:w :x :y :z], :b [:x :y :z]}
		{:a [:x :y], :b [:x :y :z]}
		{:a [:x :y :z], :b [:y :z]}]]
      (is (= '(true true false false) (map criteria-fn data))))))

(deftest fn-find-matches
  (let [m1 {:a true, :b [:x :y :z]}
	m2 {:a true, :b [:y :z]}
	m3 {:a true, :b [:x :y]}
	m4 {:a false, :b [:x :y]}]
    (is (= (list m1 m3) (find-matches {:a true, :b [:x :y]} [m1 m2 m3 m4])))))