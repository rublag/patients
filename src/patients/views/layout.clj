(ns patients.views.layout
  (:require [hiccup.page :as page]))

(def bootstrap-css-url "https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css")
(def bootstrap-css-integrity "sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl")

(defn page [data]
  (page/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:title (:title data)]
    [:link {:type "text/css"
            :rel "stylesheet"
            :href bootstrap-css-url
            :integrity bootstrap-css-integrity
            :crossorigin "anonymous"}]]
   [:body
    [:header.navbar.navbar-dark.bg-dark.navbar-expand-md.mb-4
     [:nav.container
      [:a.navbar-brand.mx-auto {:href "/" :style "font-size: 2.5rem"} "Patients"]]]
    [:main.container
     [:div.p-5.bg-light
      (:html data)]]]))
