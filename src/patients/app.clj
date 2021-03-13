(ns patients.app
  (:require [reitit.ring :as ring]))

(def router
  (ring/router
   ["/" {:get (fn [req] {:status 200 :body "ok"})}]))

(def app (ring/ring-handler router))
