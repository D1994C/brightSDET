package utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Properties;

public class Utils {

  /**
   * @param url: url of site to extract the cert info from
   * @throws Exception
   *
   * https://stackoverflow.com/questions/22464955/how-to-get-the-certificate-information-in-java

   */
  public static boolean confirmCertNotExpired(String url) throws Exception{
    URL destinationURL = new URL(url);
    HttpsURLConnection conn = (HttpsURLConnection) destinationURL.openConnection();
    conn.connect();
    Certificate[] certs = conn.getServerCertificates();
    boolean expired = false;
    for (Certificate cert : certs) {
        System.out.println(((X509Certificate) cert).getNotAfter());
        Date currentDate = new Date();
        Date sslExpire = ((X509Certificate) cert).getNotAfter();
        if(sslExpire.compareTo(currentDate) > 0){
          expired = true;
        }
    }
    return expired;
  }

  public static Properties loadProps(String propFile) throws IOException {
    InputStream input = new FileInputStream(propFile);
    Properties props = new Properties();
    props.load(input);
    return props;
  }

}
