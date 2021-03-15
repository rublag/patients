(ns patients.models.patients-test
  (:require [patients.models.patients :as sut]
            [patients.db :as db]
            [migratus.core :as migratus]
            [clojure.test :as t]
            [clojure.java.jdbc :as jdbc]
            [java-time :as time]))

;;;; FIXTURES ====================
(def ^:dynamic *trans* nil)

(def fixture-data
  [{:first-name "John"
    :last-name "Doe"
    :patronimic-name nil
    :birthday (time/sql-date 1975 3 7)
    :sex "male"
    :address "Uglegorsk"
    :oms-number "37A-2718"}
   {:first-name "Иван"
    :last-name "Петров"
    :patronimic-name "Сидорович"
    :birthday (time/sql-date 2010 4 7)
    :sex "male"
    :address "Москва, Ленинские горы, 1"
    :oms-number "7105816491758265"}
   {:first-name "Елена"
    :last-name "Иванова"
    :patronimic-name "Игоревна"
    :sex "female"
    :address "Санкт-Петербург, Ленина, 17"
    :birthday (time/sql-date 1990 2 9)
    :oms-number "1234567890123456"}])

(defn fixture-up [data]
  (migratus/reset db/migratus-conf)
  (jdbc/insert-multi! db/spec
                     :patients
                     data
                     {:entities #(.replace % \- \_)}))

(defn fixture [f]
  (fixture-up fixture-data)
  (f))

(defn transaction-fixture [f]
  (jdbc/with-db-transaction [t db/spec]
    (jdbc/db-set-rollback-only! t)
    (binding [*trans* t]
      (f))))

;;;; TESTS =====================
(t/deftest patients-list-test
  (t/testing "returns correct list"
    (t/is (= (map #(select-keys % [:first-name :last-name :patronimic-name]) (sut/patients-list))
             (map #(select-keys % [:first-name :last-name :patronimic-name]) fixture-data)))))

(t/use-fixtures :once fixture)
;(t/use-fixtures :each transaction-fixture)
