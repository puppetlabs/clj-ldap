package com.puppetlabs.ldap;

import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustStoreTrustManager;

import javax.net.ssl.TrustManager;

public class Utils {

    public static SSLUtil trustManagerToSSLUtil(final TrustManager[] tm) {
        return new SSLUtil(tm);
    }

    public static SSLUtil trustStoreToSSLUtil(String ts) {
        return new SSLUtil(new TrustStoreTrustManager(ts));
    }
}