(ns patients.utils-test
  (:require [patients.utils :as sut]
            [clojure.test :as t]
            [java-time :as time]))

(t/deftest str->int-test
  (t/testing "str->int: integers"
    (t/is (= 27 (sut/str->int "27")))
    (t/is (= 0 (sut/str->int "0"))))
  (t/testing "str->int: non-integers"
    (t/is (= nil (sut/str->int "aa")))
    (t/is (= nil (sut/str->int "1a")))
    (t/is (= nil (sut/str->int "a1")))))

(t/deftest snake->kebab-test
  (t/testing "snake->kebab"
    (t/is (= "a-b-c-d" (sut/snake->kebab "a_b-c_d")))))

(t/deftest kebab->snake-test
  (t/testing "kebab->snake"
    (t/is (= "a_b_c_d" (sut/kebab->snake "a-b_c-d")))))

(t/deftest parse-html-date-test
  (t/testing "date parsing"
    (t/is (= (time/sql-date 2020 03 06)
             (sut/parse-html-date "2020-03-06")))
    (t/is (= nil (sut/parse-html-date "2020-03-0d")))
    (t/is (= nil (sut/parse-html-date nil)))))
