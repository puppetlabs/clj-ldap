(ns clj-ldap.test.ssl-server
  (:require [clojure.test :refer :all]
            [fs.core :as fs]
            [clj-ldap.client :as ldap])
  (:import [org.apache.directory.server.core
            DefaultDirectoryService]
           [org.apache.directory.server.protocol.shared.transport
            TcpTransport]
           [org.apache.directory.server.ldap LdapServer]
           [org.apache.directory.server.ldap.handlers.extended
            StartTlsHandler]
           [com.unboundid.ldap.sdk
            LDAPConnectionPool
            LDAPException]))

(def port* 1389)
(def ssl-port* 1636)
(defonce server (atom nil))

(defn- start-ldap-server
  "Start up an embedded ldap server"
  [port ssl-port]
  (let [work-dir          (fs/temp-dir)
        directory-service (doto (DefaultDirectoryService.)
                            (.setShutdownHookEnabled true)
                            (.setWorkingDirectory work-dir))
        ldap-transport    (TcpTransport. port)
        ssl-transport     (doto (TcpTransport. ssl-port)
                            (.setEnableSSL true))
        ldap-server       (doto (LdapServer.)
                            (.setDirectoryService directory-service)
                            (.addExtendedOperationHandler (StartTlsHandler.))
                            (.setKeystoreFile "./dev-resources/keystore-local-new.jks")
                            (.setCertificatePassword "secret")
                            (.setTransports
                             (into-array [ldap-transport ssl-transport])))]
    (.startup directory-service)
    (.start ldap-server)
    [directory-service ldap-server]))

(defn stop!
  "Stops the embedded ldap server"
  []
  (if @server
    (let [[directory-service ldap-server] @server]
      (reset! server nil)
      (.stop ldap-server)
      (.shutdown directory-service))))

(defn start!
  "Starts an embedded ldap server on the given port"
  [port ssl-port]
  (stop!)
  (reset! server (start-ldap-server port ssl-port)))

(defn- test-server
  "Setup server"
  [f]
  (start! port* ssl-port*)
  (f)
  (stop!))

(use-fixtures :once test-server)

(deftest basic-connection
  (testing "pass with verify host"
    (is (= LDAPConnectionPool (type (ldap/connect {:host {:address "localhost" :port ssl-port*} :ssl? true :verify-host? true})))))
  (testing "failure with verify host"
    (is (thrown-with-msg? LDAPException #"Hostname verification failed" (ldap/connect {:host {:address "127.0.0.1" :port ssl-port*} :ssl? true :verify-host? true}))))
  (testing "pass without verify host"
    (is (= LDAPConnectionPool (type (ldap/connect {:host {:address "localhost" :port ssl-port*} :ssl? true :verify-host? false}))))
    (is (= LDAPConnectionPool (type (ldap/connect {:host {:address "127.0.0.1" :port ssl-port*} :ssl? true :verify-host? false}))))))