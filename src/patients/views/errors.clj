(ns patients.views.errors
  (:require [patients.views.layout :as layout]))

(defn error-404 []
  (layout/page {:title "Error 404 - Patients"
                :html [:h1 "The requested URL was not found."]}))

(defn error-405 []
  (layout/page {:title "Error 405 - Patients"
                :html [:h1 "The requested HTTP method is not allowed."]}))

(defn error-406 []
  (layout/page {:title "Error 406 - Patients"
                :html [:h1 "The server could not generate acceptable representation."]}))
