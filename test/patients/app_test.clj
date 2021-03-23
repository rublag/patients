(ns patients.app-test
  (:require [patients.app :as sut]
            [clojure.test :as t]
            [reitit.core :as r]))

(t/deftest app-test
  (t/testing "routing"
    (t/is (not= nil (r/match-by-path sut/router "/")))
    (t/is (not= nil (r/match-by-path sut/router "/patients/4")))
    (t/is (not= nil (r/match-by-path sut/router "/patients/new")))
    (t/is (not= nil (r/match-by-path sut/router "/patients/4/edit")))
    (t/is (not= nil (r/match-by-path sut/router "/patients/4/delete")))))
