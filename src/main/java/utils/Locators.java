package utils;

import java.io.IOException;
import java.util.Properties;

import static utils.Utils.loadProps;

public class Locators {

  private static Properties homePaths;
  private static Properties footerPaths;
  private static Properties bookingPaths;
  private static Properties accountPaths;

  static {
    try {
      homePaths = loadProps("src/resources/xpaths/homepageXpaths");
      footerPaths = loadProps("src/resources/xpaths/footerXpaths");
      bookingPaths = loadProps("src/resources/xpaths/bookingXpaths");
      accountPaths = loadProps("src/resources/xpaths/accountXpaths");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String returnHomeXpath(String xpath){
    return homePaths.getProperty(xpath);
  }

  public static String returnFooterXpath(String xpath){
    return footerPaths.getProperty(xpath);
  }

  public static String returnBookingXpath(String xpath){
    return bookingPaths.getProperty(xpath);
  }

  public static String returnAccountXpath(String xpath){ return accountPaths.getProperty(xpath); }


}
