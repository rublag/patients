(defproject patients "0.1.0-SNAPSHOT"
  :description "Web application to manage patients' records"
  :url "https://patients.rublag.xyz/"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.postgresql/postgresql "42.2.19"]
                 [environ "1.2.0"]
                 [http-kit "2.5.3"]]
  :main ^:skip-aot patients.core
  :target-path "target/%s"
  :profiles {:uberjar {:uberjar-name "patients.jar"
                       :aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
