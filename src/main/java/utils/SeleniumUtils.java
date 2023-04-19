package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.TestConfiguration.DRIVER;
import static utils.TestConfiguration.MODE;

public class SeleniumUtils {

  //Driver and Selenium specific methods

  public static WebDriver setupDriver(Duration timeout) {
    System.setProperty("webdriver.chrome.driver", DRIVER);
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");
    if(MODE.equalsIgnoreCase("headless")){
      options.addArguments("--headless");
    }
    WebDriver driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(timeout);
    return driver;
  }

  public static void stopDriver(WebDriver driver) {
    driver.close();
    driver.quit();
  }

  public static void goTo(WebDriver driver, String url){
    try{
      driver.get(url);
    }
    catch (Exception e){
      System.out.println(String.format("Error navigating to %s, exception thrown: ", url, e));
    }
  }

  public static void jsClick(WebDriver driver, WebElement element){
    JavascriptExecutor jse = (JavascriptExecutor)driver;
    jse.executeScript("arguments[0].click()", element);
  }

  public static boolean confirmElementPresent(WebDriver driver, String xpath){
    return !driver.findElements(By.xpath(xpath)).isEmpty();
  }

  public static boolean confirmElementNotClickable(WebDriver driver, String xpath){
    boolean clickable = confirmElementPresent(driver, xpath); //Start by setting boolean to if element is present as if it is not we will fail anyway
    if(clickable){
      try {
        driver.findElement(By.xpath(xpath)).click();
        clickable = false; // If we can click this is false
      }
      catch (Exception e){
        System.out.println("Element Not Clickable, proceeding");
      }
    }
    return clickable;
  }

  public static void waitForElementNotVisable(WebDriverWait wait, String xpath){
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
  }

  //Navigation methods to specific pages


  public static boolean sslWarnings(WebDriver driver)
  {
    return driver.getTitle().equals("Untrusted Connection");
  }

}
