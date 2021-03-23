(ns patients.models.patients
  "Model functions support two styles of calling:
   - (fn args) -- uses db-spec from patients.db
   - (fn db-spec args) -- uses custom db-spec"
  (:require [clojure.java.jdbc :as jdbc]
            [patients.db :as db]
            [patients.utils :as u]))

(defn patients-list
  ([db-spec]
   (jdbc/query db-spec
               ["SELECT id, first_name, patronymic_name, last_name FROM patients"]
               {:identifiers u/snake->kebab}))
  ([]
   (patients-list db/spec)))

(defn patient-info
  ([db-spec id]
   (first (jdbc/query db-spec
                      ["SELECT * FROM patients WHERE id=?" id]
                      {:identifiers u/snake->kebab})))
  ([id]
   (patient-info db/spec id)))

(defn add-patient!
  ([db-spec patient]
   (:id (first (jdbc/insert! db-spec :patients patient
                       {:entities u/kebab->snake}))))
  ([patient]
   (add-patient! db/spec patient)))

(defn update-patient!
  ([db-spec id patient]
   (jdbc/update! db-spec :patients patient
                 ["id = ?" id]
                 {:entities u/kebab->snake}))
  ([id patient] (update-patient! db/spec id patient)))

(defn delete-patient!
  ([db-spec id]
   (jdbc/delete! db-spec :patients
                 ["id = ?" id]
                 {:entities u/kebab->snake}))
  ([id]
   (delete-patient! db/spec id)))
