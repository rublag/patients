(ns patients.utils)

(defn str->int [s]
  (try (Integer/parseInt s)
       (catch NumberFormatException _ nil)))

(defn snake->kebab [kw]
  (.replace kw \_ \-))

(defn kebab->snake [kw]
  (.replace kw \- \_))
