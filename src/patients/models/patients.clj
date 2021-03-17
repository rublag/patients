(ns patients.models.patients
  "Model functions support two styles of calling:
   - (fn args) -- uses db-spec from patients.db
   - (fn db-spec args) -- uses custom db-spec"
  (:require [clojure.java.jdbc :as jdbc]
            [patients.db :as db]))

(defn patients-list
  ([db-spec]
   (jdbc/query db-spec
               ["SELECT id, first_name, patronymic_name, last_name FROM patients"]
               {:identifiers #(.replace % \_ \-)}))
  ([]
   (patients-list db/spec)))

(defn patient-info
  ([db-spec id]
   (first (jdbc/query db-spec
                      ["SELECT * FROM patients WHERE id=?" id]
                      {:identifiers #(.replace % \_ \-)})))
  ([id]
   (patient-info db/spec id)))
