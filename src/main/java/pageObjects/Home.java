package pageObjects;

import utils.Locators;
import utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static utils.TestConfiguration.HOMEPAGE;

public class Home {

  public static void navigateToHomepage(WebDriver driver){
    SeleniumUtils.goTo(driver, HOMEPAGE);
  }

  public static void loadSiteIntoRegion(WebDriver driver, String region){
    navigateToHomepage(driver);
    clickCookieConsent(driver);
    driver.findElement(By.xpath(String.format("//img[@alt='%s Region']",region))).click();
  }

  public static void clickCookieConsent(WebDriver driver){
    driver.findElement(By.xpath(Locators.returnHomeXpath("agreeToCookiesXpath"))).click();
  }

  public static void selectIrishRegion(WebDriver driver){
    driver.findElement(By.xpath(Locators.returnHomeXpath("irelandRegionFlagXpath"))).click();
  }

}
