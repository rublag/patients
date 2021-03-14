(ns patients.app
  (:require [reitit.ring :as ring]
            [patients.controllers.patients :as patients]))

(def router
  (ring/router
   ["/" {:get patients/patients-page}]))

(def app (ring/ring-handler router))
