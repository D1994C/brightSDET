package utils;

import java.io.IOException;
import java.util.Properties;

import static utils.Utils.loadProps;

public class TestConfiguration {

  private static Properties properties;
  public static final String DRIVER;
  public static final String LOGIN_USER;
  public static final String LOGIN_PASS;
  public static final String INVALID_PASS;
  public static final String HOMEPAGE;
  public static final String MODE;

  static {
    try {
      properties = loadProps("src/resources/test.properties");
    } catch (IOException e) {
      e.printStackTrace();
    }
    DRIVER = getDriver();
    LOGIN_USER = getLoginUser();
    LOGIN_PASS = getLoginPass();
    INVALID_PASS = getInvalidPass();
    HOMEPAGE = getHomepage();
    MODE = getMode();
  }

  public static String getDriver(){
    return properties.getProperty("driver");
  }

  public static String getLoginUser(){
    return properties.getProperty("loginUser");
  }

  public static String getLoginPass(){
    return properties.getProperty("loginPass");
  }

  public static String getInvalidPass(){
    return properties.getProperty("invalidPass");
  }

  public static String getHomepage(){
    return properties.getProperty("homepageUrl");
  }

  public static String getMode(){
    return properties.getProperty("mode");
  }
}
