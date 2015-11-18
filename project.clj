(defproject default "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://github.com/wgq91here"
  :license {:name "Apache License 2.0"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.cli "0.3.3"]
                 [mvxcvi/puget "1.0.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.2"]
                 [ring/ring-anti-forgery "1.0.0"]
                 [ring/ring-devel "1.4.0"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [log4j "1.2.15" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [korma "0.4.2"]
                 ;                 [lobos "1.0.0-beta3"]
                 [org.xerial/sqlite-jdbc "3.8.11.2"]
                 [nginx-clojure "0.4.3"]]
  :main chandra.core
  :min-lein-version "2.0.0"
;  :target-path "target/%s"
  :uberjar-name "chandra.jar"
  :jvm-opts ["-server"]
  :profiles {
              :provided {:dependencies [[nginx-clojure "0.4.3"]]}
              :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                   [ring-mock "0.1.5"]]}
              :embed {:dependencies [[nginx-clojure/nginx-clojure-embed "0.4.3"]]
                      :aot [clojure-web-example.embed-server]
                      :main clojure-web-example.embed-server
                      :uberjar-name "clojure-web-example-embed.jar"
                      }
              }
  )