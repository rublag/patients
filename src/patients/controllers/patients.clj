(ns patients.controllers.patients
  (:require [patients.views.layout :as layout]
            [patients.views.patients :as view]
            [patients.models.patients :as model]
            [patients.utils :as u]
            [clojure.string :as str]))

(defn patients-page "Return Ring response for patients page"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (layout/page {:title "Patients"
                       :html (view/patients-list (model/patients-list))})})

(defn patient-info-page [req]
  (if-let [id (u/str->int (get-in req [:path-params :id]))]
    (if-let [patient (model/patient-info id)]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (layout/page {:title (str/join " " [(:last-name patient)
                                                 (:first-name patient)
                                                 (:patronimic-name patient)])
                           :html (view/patient-info patient)})}
      {:status 404})
    {:status 404}))

(defn patient-new-page [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (layout/page {:title "Add new patient"
                       :html (view/add-patient)})})
