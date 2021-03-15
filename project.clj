(defproject patients "0.1.0-SNAPSHOT"
  :description "Web application to manage patients' records"
  :url "https://patients.rublag.xyz/"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.postgresql/postgresql "42.2.19"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [environ "1.2.0"]
                 [http-kit "2.5.3"]
                 [migratus "1.3.5"]
                 [metosin/reitit "0.5.12"]
                 [hiccup "1.0.5"]
                 [ring/ring-devel "1.9.1"]
                 [honeysql "1.0.461"]
                 [clojure.java-time "0.3.2"]]
  :main ^:skip-aot patients.core
  :target-path "target/%s"
  :profiles {:uberjar {:uberjar-name "patients.jar"
                       :aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
