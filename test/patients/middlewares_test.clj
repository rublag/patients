(ns patients.middlewares-test
  (:require [patients.middlewares :as sut]
            [clojure.test :as t]
            [patients.views.errors :as errors]))

(t/deftest wrap-404-test
  (t/testing "HTTP error 404 wrapper"
    (with-redefs [patients.views.errors/error-404 (constantly "error body")]
      (t/is (= {:status 404
                :headers {"Content-Type" "text/html"}
                :body "error body"}
               ((sut/wrap-404 (constantly {:status 404 :body "something"})) {})))
      (t/is (= {:status 200
                :body "something"}
               ((sut/wrap-404 (constantly {:status 200 :body "something"})) {}))))))

(t/deftest wrap-405-test
  (t/testing "HTTP error 405 wrapper"
    (with-redefs [patients.views.errors/error-405 (constantly "error body")]
      (t/is (= {:status 405
                :headers {"Content-Type" "text/html"}
                :body "error body"}
               ((sut/wrap-405 (constantly {:status 405 :body "something"})) {})))
      (t/is (= {:status 200
                :body "something"}
               ((sut/wrap-405 (constantly {:status 200 :body "something"})) {}))))))

(t/deftest wrap-406-test
  (t/testing "HTTP error 406 wrapper"
    (with-redefs [patients.views.errors/error-406 (constantly "error body")]
      (t/is (= {:status 406
                :headers {"Content-Type" "text/html"}
                :body "error body"}
               ((sut/wrap-406 (constantly {:status 406 :body "something"})) {})))
      (t/is (= {:status 200
                :body "something"}
               ((sut/wrap-406 (constantly {:status 200 :body "something"})) {}))))))

(t/deftest wrap-errors
  (t/testing "HTTP errors wrapper"
    (t/is (= (errors/error-404)
             (:body ((sut/wrap-404 (constantly {:status 404})) {}))))
    (t/is (= (errors/error-405)
             (:body ((sut/wrap-405 (constantly {:status 405})) {}))))
    (t/is (= (errors/error-406)
             (:body ((sut/wrap-406 (constantly {:status 406})) {}))))
    (t/is (= 200
             (:status ((sut/wrap-404 (constantly {:status 200})) {}))))))
