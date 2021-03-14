(ns patients.app
  (:require [reitit.ring :as ring]
            [ring.middleware.reload :as reload]
            [ring.middleware.lint :as lint]
            [patients.controllers.patients :as patients]))

(def router
  (ring/router
   ["/" {:get patients/patients-page}]))

(def app (ring/ring-handler router))

(def dev-app (-> #'app
                 reload/wrap-reload
                 lint/wrap-lint))
