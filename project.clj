(defproject tuic "0.1.0-SNAPSHOT"
  :description "Tuic"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.7.0-alpha1"]
                 [org.postgresql/postgresql "9.4.1212"]
                 [joda-time "2.9.6"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.5.1"]
                 [com.cemerick/friend "0.2.3"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler tuic.core/app}
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
