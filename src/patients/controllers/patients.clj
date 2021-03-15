(ns patients.controllers.patients
  (:require [patients.views.layout :as layout]))

(defn patients-page "Return Ring response for patients page"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (layout/page {:title "Hello" :html [:h1 "World!"]})})
