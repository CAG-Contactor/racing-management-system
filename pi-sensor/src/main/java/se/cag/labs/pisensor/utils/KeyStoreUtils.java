package se.cag.labs.pisensor.utils;

import lombok.extern.log4j.Log4j;

import javax.net.ssl.*;
import java.io.*;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * A util for creating and populate keystores with certificates.
 *
 * @author Joacim Breiler
 */
@Log4j
public class KeyStoreUtils {

    private static final String SSL_CONTEXT = "SSL";

    /**
     * Creates a new keystore and downloads certificates to it.
     *
     * @param hostname the hostname for the server which certificates we want to
     *                 fetch.
     * @param port     the SSL port that is used by the server
     * @param filename the filename of the keystore to be created
     * @param password the password of the keystore
     * @throws IOException
     */
    public static void updateKeyStore(String hostname, int port, String filename, String password) throws IOException {
        File keystoreFile = new File(filename);

        try {
            List<Certificate> certificates = retrieveAllCertificates(hostname, port);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            if (!keystoreFile.exists()) {
                keyStore.load(null, password.toCharArray());
            } else {
                keyStore.load(new FileInputStream(keystoreFile), password.toCharArray());
            }

            addCertificatesToKeyStore(keyStore, certificates);
            FileOutputStream out = new FileOutputStream(new File(filename));
            keyStore.store(out, password.toCharArray());
            out.close();
        } catch (KeyManagementException | CertificateException e) {
            throw new IOException("Certificate contained errors and couldn't be stored", e);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Certificate contained an unknown algorithm and couldn't be stored", e);
        } catch (KeyStoreException e) {
            throw new IOException("Couldn't create keystore", e);
        } catch (FileNotFoundException e) {
            throw new IOException("Couldn't create keystore file", e);
        }
    }

    private static void addCertificatesToKeyStore(KeyStore keyStore, List<Certificate> certificates) throws KeyStoreException {
        for (Certificate c : certificates) {
            String name = "certificate-" + c.hashCode();
            if (c instanceof X509Certificate) {
                name = ((X509Certificate) c).getSubjectX500Principal().getName();
            }
            keyStore.setCertificateEntry(name, c);
        }
    }

    /**
     * Connects to server and downloads and returns it's certificates.
     *
     * @param hostname the hostname of the server.
     * @param port     the port of the server.
     * @return a list of all certificates.
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws UnknownHostException
     * @throws IOException
     */
    public static List<Certificate> retrieveAllCertificates(String hostname, int port) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLSocket socket = null;
        try {
            SSLContext sc = createSSLContextWithoutTrust();

            // Create a SSL socket
            SSLSocketFactory factory = sc.getSocketFactory();
            socket = (SSLSocket) factory.createSocket(hostname, port);
            socket.startHandshake();

            // Returns an ordered array of peer certificates, with the peer's
            // own certificate first followed by any certificate authorities.
            return Arrays.asList(socket.getSession().getPeerCertificates());
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    private static SSLContext createSSLContextWithoutTrust() throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = createTrustAllManager();

        // Create a SSL context
        SSLContext sc = SSLContext.getInstance(SSL_CONTEXT);
        sc.init(null, trustAllCerts, new SecureRandom());
        return sc;
    }

    /**
     * Creates a list of TrustsManagers that trusts all certificates.
     *
     * @return an array with trust managers.
     */
    private static TrustManager[] createTrustAllManager() {
        return new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
    }
}
