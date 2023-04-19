import utils.Locators;
import utils.Utils;
import utils.SeleniumUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.Account;
import pageObjects.Booking;
import pageObjects.Footer;
import pageObjects.Home;

import java.time.Duration;

import static utils.TestConfiguration.HOMEPAGE;
import static utils.TestConfiguration.INVALID_PASS;
import static utils.TestConfiguration.LOGIN_PASS;
import static utils.TestConfiguration.LOGIN_USER;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class of test cases for the brightpay demo showcasing a quick POC of a Selenium framework to test the brightpay site
 */
public class TestCases {

  private static WebDriver driver;
  private static WebDriverWait wait;
  private static final String IRELAND = "Ireland";
  private static final String IRELAND_URL = "https://brightsg.com/en-ie";
  private static final String UK = "UK";

  @BeforeEach
  public void setUp(){
    driver = SeleniumUtils.setupDriver(Duration.ofSeconds(10));
    driver.manage().window().maximize();
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void cleanUp(){
    SeleniumUtils.stopDriver(driver);
  }

  @Tag("Security")
  @Test
  public void confirmValidSSLCert() throws Exception {
    assertTrue(Utils.confirmCertNotExpired(HOMEPAGE));
  }

  @Tag("Cookies")
  @Test
  public void confirmCookieConsentRequired(){
    Home.navigateToHomepage(driver);
    assertTrue(SeleniumUtils.confirmElementPresent(driver, Locators.returnHomeXpath("cookieConsentBoxXpath")));
    assertTrue(SeleniumUtils.confirmElementNotClickable(driver, Locators.returnHomeXpath("homeBannerXpath")));
    Home.clickCookieConsent(driver);
    SeleniumUtils.waitForElementNotVisable(wait, Locators.returnHomeXpath("cookieConsentBoxXpath"));

    //Potential to expand here to confirm the actual cookie is present using Selenium's manage methods. However I believe that is beyond the scope of this demo
  }

  @Tag("Cookies")
  @Test
  public void confirmRegionSelectRequired(){
    Home.navigateToHomepage(driver);
    Home.clickCookieConsent(driver);
    assertTrue(SeleniumUtils.confirmElementPresent(driver, Locators.returnHomeXpath("regionSelectBoxXpath")));
    assertTrue(SeleniumUtils.confirmElementNotClickable(driver,Locators.returnHomeXpath("homeBannerXpath")));
    Home.selectIrishRegion(driver);
    SeleniumUtils.waitForElementNotVisable(wait, Locators.returnHomeXpath("regionSelectBoxXpath"));
    assertTrue(driver.getCurrentUrl().equals(IRELAND_URL));
  }

  @Tag("Footer")
  @Test
  public void confirmRegionCanBeChanged() {
    Home.loadSiteIntoRegion(driver, IRELAND); //Setup the site on the Irish Region To Start
    assertTrue(Footer.changeRegion(driver, wait, UK));
  }

  @Tag("Account")
  @Test
  public void loginAndConfirmProfileDetails(){
    Account.navigateToLoginScreen(driver, wait);
    Account.submitLoginDetails(driver, LOGIN_USER, LOGIN_PASS);
    Account.navigateToProfile(driver, wait);
    assertTrue(Account.validateUserData(driver, wait));
  }

  @Tag("Account")
  @Test
  public void cannotLoginWithIncorrectPass(){
    Account.navigateToLoginScreen(driver, wait);
    Account.submitLoginDetails(driver, LOGIN_USER, INVALID_PASS);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Locators.returnAccountXpath("incorrectPasswordBoxXpath"))));
  }

  @Tag("Booking")
  @Test
  public void userIsAbleToBookSoftwareDemo(){
    Home.loadSiteIntoRegion(driver, IRELAND);
    assertTrue(Booking.bookDemoAndValidate(driver, wait));
  }

  @Disabled
  /**
   * Test disabled to to extra login redirect needed for Linkedin - I believe this is beyond the scope of this demo.
   * To see the methods working please simply comment out the linkedin check in the method
   */
  @Tag("Footer")
  @Test
  public void confirmSocialLinksAreCorrect(){
    Home.loadSiteIntoRegion(driver, IRELAND);
    assertTrue(Footer.validateSocialLinks(driver, wait));
  }
}


