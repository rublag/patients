(ns patients.app
  (:require [reitit.ring :as ring]
            [ring.middleware.reload :as reload]
            [ring.middleware.lint :as lint]
            [patients.controllers.patients :as patients]
            [patients.middlewares :as mw]))

(def router
  (ring/router
   [""
    ["/" {:get patients/patients-page}]
    ["/patients"
     ["/:id" {:get patients/patient-info-page}]]]))

(def app (-> (ring/ring-handler router)
             mw/wrap-errors))

(def dev-app (-> #'app
                 reload/wrap-reload
                 lint/wrap-lint))
