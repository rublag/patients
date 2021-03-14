(ns patients.views.layout
  (:require [hiccup.page :as page]))

(defn page []
  (page/html5
   [:head
    [:title "Hello, World!"]]
   [:body
    [:h1 "Hello, World!"]]))
