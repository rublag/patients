(ns patients.middlewares
  (:require [patients.views.errors :as errors]
            [ring.util.response :as response]))

(defn wrap-404 [handler]
  (fn [request]
    (let [response (handler request)]
      (if (= 404 (:status response))
        (-> (assoc response :body
                   (errors/error-404))
            (response/content-type "text/html"))
        response))))

(defn wrap-405 [handler]
  (fn [request]
    (let [response (handler request)]
      (if (= 405 (:status response))
        (-> (assoc response :body
                   (errors/error-405))
            (response/content-type "text/html"))
        response))))

(defn wrap-406 [handler]
  (fn [request]
    (let [response (handler request)]
      (if (= 406 (:status response))
        (-> (assoc response :body
                   (errors/error-406))
            (response/content-type "text/html"))
        response))))

(defn wrap-errors [handler]
  (-> handler
      wrap-404
      wrap-405
      wrap-406))
