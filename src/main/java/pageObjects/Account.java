package pageObjects;

import utils.Locators;
import utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class Account {

  private static final String accEmail = "close995@gmail.com";
  private static final String accName = "Darren Close";
  private static final String accComp = "TestAccount";
  private static final String accRegion = "United Kingdom";



  public static void navigateToLoginScreen(WebDriver driver, WebDriverWait wait){
    Home.loadSiteIntoRegion(driver , "Ireland"); // Region does not matter due to navigating away to login page
    Actions builder = new Actions(driver);
    builder.moveToElement(driver.findElement(By.xpath(Locators.returnAccountXpath("loginDropdownXpath")))).perform();
    driver.findElement(By.xpath(Locators.returnAccountXpath("brightIDXpath"))).click();
    ArrayList tabs = new ArrayList(driver.getWindowHandles());
    driver.switchTo().window(tabs.get(1).toString()); //Login is in a new tab so need to switch here
    wait.until(ExpectedConditions.urlContains("login.brightsg.com"));
  }

  public static void submitLoginDetails(WebDriver driver, String user, String pass){
    driver.findElement(By.xpath(Locators.returnAccountXpath("loginNameXpath"))).sendKeys(user);
    driver.findElement(By.xpath(Locators.returnAccountXpath("loginPassXpath"))).sendKeys(pass);
    WebElement submitBtn = driver.findElement(By.xpath(Locators.returnAccountXpath("loginSubmitXpath")));
    SeleniumUtils.jsClick(driver, submitBtn);
  }

  public static void navigateToProfile(WebDriver driver, WebDriverWait wait){
    WebElement profile = driver.findElement(By.xpath(Locators.returnAccountXpath("accountOverviewXpath")));
    wait.until(ExpectedConditions.elementToBeClickable(profile));
    profile.click();
  }

  public static boolean validateUserData(WebDriver driver, WebDriverWait wait){
    WebElement email = driver.findElement(By.xpath(getProfileXpath("Email address")));
    wait.until(ExpectedConditions.textToBePresentInElement(email, accEmail)); //wait until the label appears

    WebElement name = driver.findElement(By.xpath(getProfileXpath("Name")));
    wait.until(ExpectedConditions.textToBePresentInElement(name, accName)); //wait until the label appears

    WebElement company = driver.findElement(By.xpath(getProfileXpath("Company name")));
    wait.until(ExpectedConditions.textToBePresentInElement(company, accComp)); //wait until the label appears

    WebElement region = driver.findElement(By.xpath(getProfileXpath("Region")));
    wait.until(ExpectedConditions.textToBePresentInElement(region, accRegion)); //wait until the label appears


    return true; //Return only if all the above pass
  }

  /**
   * Small mathod to generate the xpaths at runtime for the profile labels so we don't need to manually have 4
   * @param label - Label on the UI to generate the Xpath for
   * @return - Xpath with label inserted
   */
  public static String getProfileXpath(String label){
    return String.format("//label[contains(text(), '%s')]/parent::div", label);
  }
}
