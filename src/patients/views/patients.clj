(ns patients.views.patients
  (:require [hiccup.page :as page]
            [clojure.string :as str]))

(defn patients-list-entry [patient]
  (let [{:keys [id first-name last-name patronimic-name]} patient
        name (str/join " " [last-name first-name patronimic-name])]
    [:a.list-group-item.list-group-item-action {:href (str "/patients/" id)} name]))

(defn patients-list [patients]
  [:div.list-group
   (map patients-list-entry patients)])

(defn render-keyword [kw]
  (str/capitalize (.replace (name kw) \- \space)))

(defn render-info-item [patient item]
  (when-let [value (item patient)]
    [:li (str (render-keyword item) ": " value)]))

(defn patient-info [patient]
  [:ul.list-unstyled
   (remove nil?
           (map #(render-info-item patient %)
                [:last-name :first-name :patronimic-name :sex :birthday
                 :address :oms-number]))])
