(ns patients.views.patients
  (:require [hiccup.form :as form]
            [clojure.string :as str]))

(defn patients-list-entry [patient]
  (let [{:keys [id first-name last-name patronymic-name]} patient
        name (str/join " " [last-name first-name patronymic-name])]
    [:a.list-group-item.list-group-item-action {:href (str "/patients/" id)} name]))

(defn patients-list [patients]
  [:div
   [:div.d-grid.gap-2
    [:a.btn.btn-primary {:href (str "/patients/new")} "Add new patient"]]
   [:div.list-group
        (map patients-list-entry patients)]])

(defn render-keyword [kw]
  (str/capitalize (.replace (name kw) \- \space)))

(defn render-info-item [patient item]
  (when-let [value (item patient)]
    [:li (str (render-keyword item) ": " value)]))

(defn patient-info [patient]
  (seq
   [[:ul.list-unstyled
     (remove nil?
             (map #(render-info-item patient %)
                  [:last-name :first-name :patronymic-name :sex :birthday
                   :address :oms-number]))]
    [:div.d-grid.gap-2
     [:a.btn.btn-primary {:href (str "/patients/" (:id patient) "/edit")} "Edit patient"]]]))

(defn patient-form
  ([action submit-text errors defaults]
   [:form {:action action :method "POST"}
    [:div.form-floating.mb-3
     [:input#last-name-input.form-control {:placeholder "Last name"
                                           :name :last-name
                                           :value (:last-name defaults)}]
     [:label {:for :last-name-input} "Last name"]]
    [:div.form-floating.mb-3
     [:input#first-name-input.form-control {:placeholder "First name"
                                            :name :first-name
                                            :required true
                                            :class (when (:first-name errors) "is-invalid")
                                            :value (:first-name defaults)}]
     [:label {:for :first-name-input} "First name"]
     [:div.invalid-feedback "Please enter the patient's name"]]
    [:div.form-floating.mb-3
     [:input#patronymic-name-input.form-control {:placeholder "Patronymic name"
                                                 :name :patronymic-name
                                                 :value (:patronymic-name defaults)}]
     [:label {:for :patronymic-name-input} "Patronymic name"]]
    [:div.form-floating.mb-3
     [:input#address-input.form-control {:placeholder "Address"
                                         :name :address
                                         :class (when (:address errors) "is-invalid")
                                         :value (:address defaults)}]
     [:label {:for :address-input} "Address"]]
    [:div.form-floating.mb-3
     [:input#oms-input.form-control {:placeholder "OMS number"
                                     :name :oms-number
                                     :required true
                                     :class (when (:oms-number errors) "is-invalid")
                                     :value (:oms-number defaults)}]
     [:label {:for :oms-input} "OMS number"]
     [:div.invalid-feedback "Please enter the patient's OMS number"]]
    [:div.form-floating.mb-3
     [:input#birthday-input.form-control {:type :date
                                          :name :birthday
                                          :placeholder "Date of birth"
                                          :required true
                                          :class (when (:birthday errors) "is-invalid")
                                          :value (:birthday defaults)}]
     [:label {:for :birthday-input} "Date of birth"]
     [:div.invalid-feedback "Please choose the patient's date of birth"]]
    [:div.mb-3
     [:div.form-check.form-check-inline
      [:input#sex-male-input.form-check-input {:type :radio
                                               :name :sex
                                               :value :male
                                               :required true
                                               :class (when (:sex errors) "is-invalid")
                                               :checked (= "male" (:sex defaults))}]
      [:label.form-check-label {:for :sex-male-input} "Male"]]
     [:div.form-check.form-check-inline
      [:input#sex-female-input.form-check-input {:type :radio
                                                 :name :sex
                                                 :value :female
                                                 :required true
                                                 :class (when (:sex errors) "is-invalid")
                                                 :checked (= "female" (:sex defaults))}]
      [:label.form-check-label {:for :sex-female-input} "Female"]]
     [:div
      [:input.d-none {:type :radio
                      :name :sex
                      :value ""
                      :required true
                      :class (when (:sex errors) "is-invalid")}]
      [:div.invalid-feedback "Please choose the patient's sex"]]]
    [:div.d-grid.gap-2
     [:input.btn.btn-primary {:type :submit :value submit-text}]]])
  ([action submit-text errors]
   (patient-form action submit-text errors {})))

(defn add-patient
  ([errors]
   (seq [[:h2.mb-4 "Add new patient"]
         (patient-form "/patients/new" "Add new patient" errors)]))
  ([] (add-patient #{})))

(defn edit-patient
  ([patient errors]
   (seq [[:h2.mb-4 "Edit patient"]
         (patient-form (str "/patients/" (:id patient) "/edit")
                       "Edit patient" errors patient)]))
  ([patient] (edit-patient patient #{})))
