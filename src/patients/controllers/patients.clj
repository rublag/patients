(ns patients.controllers.patients
  (:require [patients.views.layout :as layout]
            [patients.views.patients :as view]
            [patients.models.patients :as model]))

(defn patients-page "Return Ring response for patients page"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (layout/page {:title "Patients"
                       :html (view/patients-list (model/patients-list))})})
