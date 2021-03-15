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
