(ns patients.db
  (:require [environ.core :refer [env]]))

(def spec {:dbtype "postgresql"
           :dbname (env :postgres-database)
           :host (env :postgres-host)
           :user (env :postgres-user)
           :password (env :postgres-password)
           :port (env :postgres-port)})

(def migratus-conf {:store :database
                    :db spec})
