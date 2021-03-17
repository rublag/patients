(ns patients.controllers.patients-test
  (:require [patients.controllers.patients :as sut]
            [clojure.test :as t]
            [patients.utils :as u]))

(t/deftest patients-page-test
  (t/testing "patients page"
    (with-redefs [patients.views.layout/page identity
                  patients.views.patients/patients-list identity
                  patients.models.patients/patients-list (constantly [:a :b :c :d])]
      (t/is (= {:status 200
                :headers {"Content-Type" "text/html"}
                :body {:title "Patients"
                       :html [:a :b :c :d]}}
               (sut/patients-page {}))))))

(t/deftest patient-info-page-test
  (t/testing "patient info page: path parameters"
    (with-redefs [patients.models.patients/patient-info (constantly {})]
      (t/is (= 404 (:status (sut/patient-info-page {}))))
      (t/is (= 404 (:status (sut/patient-info-page {:path-params {}}))))
      (t/is (= 404 (:status (sut/patient-info-page {:path-params {:id "abc"}}))))
      (t/is (= 200 (:status (sut/patient-info-page {:path-params {:id "123"}}))))))
  (t/testing "patients info page: non-existent id"
    (with-redefs [patients.models.patients/patient-info (constantly nil)]
      (t/is (= 404 (:status (sut/patient-info-page {:path-params {:id "1"}}))))))
  (t/testing "patients info page: model interaction"
    (with-redefs [patients.views.layout/page identity
                  patients.views.patients/patient-info identity
                  patients.models.patients/patient-info (constantly {:last-name "a"
                                                                     :first-name "b"
                                                                     :patronymic-name "c"})]
      (t/is (= {:status 200
                :headers {"Content-Type" "text/html"}
                :body {:title "a b c"
                       :html {:last-name "a"
                              :first-name "b"
                              :patronymic-name "c"}}}
               (sut/patient-info-page {:path-params {:id "1"}}))))))

(t/deftest validate-sex-test
  (t/testing "validate-sex"
    (t/is (= [{:sex "male"} #{}]
             (sut/validate-sex [{:sex "male"} #{}])))
    (t/is (= [{:sex "female"} #{}]
             (sut/validate-sex [{:sex "female"} #{}])))
    (t/is (= [{:sex "abc"} #{:sex}]
             (sut/validate-sex [{:sex "abc"} #{}])))))

(t/deftest parse-birthday-test
  (t/testing "parse-birthday"
    (t/is (= [{:birthday (u/parse-html-date "2020-03-06")} #{}]
             (sut/parse-birthday [{:birthday "2020-03-06"} #{}])))
    (t/is (= [{:birthday "abc"} #{:birthday}]
             (sut/parse-birthday [{:birthday "abc"} #{}])))))

(t/deftest validate-presence-test
  (t/testing "validate-presence"
    (t/is (= [{:k1 :v1 :k2 :v2 :k3 :v3 :k4 :v4 :k5 nil} #{:k5 :k6}]
             (sut/validate-presence [{:k1 :v1 :k2 :v2 :k3 :v3 :k4 :v4 :k5 nil} #{}]
                                    [:k2 :k4 :k5 :k6])))))

(t/deftest replace-empty-strings-test
  (t/testing "replace-empty-strings"
    (t/is (= [{:k1 nil "" nil :k2 "value" :k3 :v} #{}]
             (sut/replace-blank-strings [{:k1 "" "" "" :k2 "value" :k3 :v} #{}])))))
