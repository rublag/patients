(ns patients.app
  (:require [reitit.ring :as ring]
            [reitit.ring.middleware.parameters :as parameters]
            [ring.middleware.reload :as reload]
            [ring.middleware.lint :as lint]
            [patients.controllers.patients :as patients]
            [patients.middlewares :as mw]))

(def router
  (ring/router
   [""
    ["/" {:get patients/patients-page}]
    ["/patients"
     ["/new" {:get patients/patient-new-page
              :post patients/patient-new-page-post
              :middleware [parameters/parameters-middleware]
              :conflicting true}]
     ["/:id" {:get patients/patient-info-page
              :conflicting true}]]]))

(def app (-> (ring/ring-handler router
                                (ring/create-default-handler))
             mw/wrap-errors))

(def dev-app (-> #'app
                 reload/wrap-reload
                 lint/wrap-lint))
