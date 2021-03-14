(ns patients.controllers.patients
  (:require [patients.views.layout :as layout]))

(defn patients-page [req]
  "Return Ring response for patients page"
  {:status 200
   :body (layout/page)})
