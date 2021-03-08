(ns patients.core
  (:use org.httpkit.server)
  (:gen-class))

(defn app [req]
  {:status 200
   :headers {"Content-type" "text/html"}
   :body "hello HTTP!"})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-server app {:port 8080})
  (println "Server started."))
