(defproject puppetlabs/clj-ldap "0.3.1-SNAPSHOT"
  :description "Clojure ldap client (Puppet Labs's fork)."
  :url "https://github.com/puppetlabs/clj-ldap"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.unboundid/unboundid-ldapsdk "6.0.5"]]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :profiles {:dev {:dependencies [[jline "0.9.94"]
                                  [org.apache.directory.server/apacheds-core-avl "1.5.6"]
                                  [org.apache.directory.server/apacheds-all "1.5.5"
                                   :exclusions [org.apache.directory.server/apacheds-core-avl]]
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
