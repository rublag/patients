(ns patients.core
  (:require [org.httpkit.server :refer :all]
            [migratus.core :as migratus]
            [patients.db :as db])
  (:gen-class))

(defn app [req]
  {:status 200
   :headers {"Content-type" "text/html"}
   :body "hello HTTP!"})

(defn -main
  [& args]
  (migratus/migrate db/migratus-conf)
  (run-server app {:port 8080})
  (println "Server started."))
