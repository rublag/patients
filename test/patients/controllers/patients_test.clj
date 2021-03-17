(ns patients.controllers.patients-test
  (:require [patients.controllers.patients :as sut]
            [clojure.test :as t]))

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
