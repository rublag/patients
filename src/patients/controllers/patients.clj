(ns patients.controllers.patients
  (:require [patients.views.layout :as layout]))

(defn patients-page [req]
  "Return Ring response for patients page"
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (layout/page {:title "Hello" :html [:h1 "World!"]})})
