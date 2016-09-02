(defproject tuic "0.1.0-SNAPSHOT"
  :description "Tuic"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.novemberain/monger "3.0.2"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [com.cemerick/friend "0.2.3"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler tuic.core/app-handler}
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
