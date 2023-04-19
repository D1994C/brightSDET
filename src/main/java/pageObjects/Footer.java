package pageObjects;

import utils.Locators;
import utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class Footer {

  public static boolean changeRegion(WebDriver driver, WebDriverWait wait, String region){

    //JavaScript clicking required due to responsive layout of site hiding buttons

    WebElement dropDownElement = driver.findElement(By.xpath(Locators.returnFooterXpath("regionDropDownXpath")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.returnFooterXpath("regionDropDownXpath"))));
    SeleniumUtils.jsClick(driver, dropDownElement);
    WebElement regionElement = driver.findElement(By.xpath(String.format("//button[contains(text(), '%s')]", region)));
    SeleniumUtils.jsClick(driver, regionElement);
    if(region.equals("UK")){
      wait.until(ExpectedConditions.urlToBe("https://brightsg.com/"));
      return true;
    }
    if(region.equals("Ireland")){
      wait.until(ExpectedConditions.urlToBe("https://brightsg.com/en-ie"));
      return true;
    }
    else{
      return false;
    }
  }

  public static boolean validateSocialLinks(WebDriver driver, WebDriverWait wait){
    validateLinks(driver, wait, "facebook", "https://www.facebook.com/brightsoftwaregroup/");
    validateLinks(driver, wait, "tiktok", "https://www.tiktok.com/@brightsg");
    validateLinks(driver, wait, "twitter", "https://twitter.com/brightsg_");
    validateLinks(driver, wait, "linkedin", "https://www.linkedin.com/company/brightsg/");
    validateLinks(driver, wait, "instagram", "https://www.instagram.com/brightsoftwaregroup/");
    return true; //Only return if all above pass
  }

  private static void validateLinks(WebDriver driver, WebDriverWait wait, String socialPlatform, String expectedUrl){
    WebElement icon = driver.findElement(By.xpath(String.format("//a[contains(@href,'%s')]", socialPlatform)));
    SeleniumUtils.jsClick(driver, icon);
    ArrayList tabs = new ArrayList(driver.getWindowHandles());
    driver.switchTo().window(tabs.get(1).toString()); //Move to tab we have just opened
    wait.until(ExpectedConditions.urlContains(expectedUrl));
    driver.close(); //Close tab
    driver.switchTo().window(tabs.get(0).toString()); //Move back to first tab
  }

}
