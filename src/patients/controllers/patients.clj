(ns patients.controllers.patients
  (:require [patients.views.layout :as layout]
            [patients.views.patients :as view]
            [patients.models.patients :as model]
            [patients.utils :as u]
            [clojure.string :as str]
            [clojure.walk :as walk]
            [ring.util.response :as response]))

(defn patients-page "Return Ring response for patients page"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (layout/page {:title "Patients"
                       :html (view/patients-list (model/patients-list))})})

(defn patient-info-page [req]
  (if-let [id (u/str->int (get-in req [:path-params :id]))]
    (if-let [patient (model/patient-info id)]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (layout/page {:title (str/join " " [(:last-name patient)
                                                 (:first-name patient)
                                                 (:patronymic-name patient)])
                           :html (view/patient-info patient)})}
      {:status 404})
    {:status 404}))

(defn patient-new-page [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (layout/page {:title "Add new patient"
                       :html (view/add-patient)})})

(defn wrap-errors [params]
  [params #{}])

(defn validate-sex [[params errors]]
  (let [sex (:sex params)]
    (if (or (= "male" sex) (= "female" sex))
      [params errors]
      [params (conj errors :sex)])))

(defn parse-birthday [[params errors]]
  (let [birthday (u/parse-html-date (:birthday params))]
    (if birthday
      [(assoc params :birthday birthday) errors]
      [params (conj errors :birthday)])))

(defn replace-blank-strings [[params errors]]
  [(into {} (map (fn [[k v]]
                   (if (and (string? v) (str/blank? v))
                     [k nil]
                     [k v]))
                 params))
   errors])

(defn validate-presence [[params errors] required-keys]
  [params (apply conj errors
                (filter #(not (% params)) required-keys))])

(defn parse-params [params]
  (-> params
      walk/keywordize-keys
      wrap-errors
      replace-blank-strings
      (validate-presence [:birthday :first-name :oms-number :sex])
      parse-birthday
      validate-sex))

(defn patient-new-page-post [req]
  (let [[params errors] (parse-params (:form-params req))]
    (if (empty? errors)
      (let [id (model/add-patient! params)]
        (-> (str "/patients/" id)
            (response/redirect :see-other)
            (response/content-type "text/html")))
      {:status 422
       :headers {"Content-type" "text/html"}
       :body (layout/page {:title "Add new patient"
                           :html (view/add-patient errors)})})))

(defn patient-edit-page [req]
  (if-let [id (u/str->int (get-in req [:path-params :id]))]
    (if-let [patient (model/patient-info id)]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (layout/page {:title (str/join " " ["Edit:"
                                                 (:last-name patient)
                                                 (:first-name patient)
                                                 (:patronymic-name patient)
                                                 "- Patients"])
                           :html (view/edit-patient patient)})}
      {:status 404})
    {:status 404}))
