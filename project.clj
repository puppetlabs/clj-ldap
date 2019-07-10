(defproject puppetlabs/clj-ldap "0.2.2-SNAPSHOT"
  :description "Clojure ldap client (Puppet Labs's fork)."
  :url "https://github.com/puppetlabs/clj-ldap"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.unboundid/unboundid-ldapsdk "4.0.11"]]
  :profiles {:dev {:dependencies [[jline "0.9.94"]
                                  [org.apache.directory.server/apacheds-all "1.5.5"]
                                  [fs "1.1.2"]
                                  [org.slf4j/slf4j-simple "1.5.6"]]
                   :jvm-opts ["-Djava.security.properties=./dev-resources/java.security"]}}


  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/clojars_jenkins_username
                                     :password :env/clojars_jenkins_password
                                     :sign-releases false}]]
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "same as Clojure"})
