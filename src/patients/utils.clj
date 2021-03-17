(ns patients.utils
  (:require [java-time :as time]))

(defn str->int [s]
  (try (Integer/parseInt s)
       (catch NumberFormatException _ nil)))

(defn snake->kebab [kw]
  (.replace kw \_ \-))

(defn kebab->snake [kw]
  (.replace kw \- \_))

(defn parse-html-date [date]
  (if (nil? date)
    nil
    (try (time/sql-date (time/local-date "yyyy-MM-dd" date))
             (catch clojure.lang.ExceptionInfo e
               (if (= "Conversion failed" (ex-message e))
                 nil
                 (throw e))))))
