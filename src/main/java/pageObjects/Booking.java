package pageObjects;

import utils.Locators;
import utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

public class Booking {

  public static void navigateToBookingPage(WebDriver driver, WebDriverWait wait){
    WebElement bookBtn = driver.findElement(By.xpath(Locators.returnBookingXpath("bookingButtonXpath")));
    wait.until(ExpectedConditions.elementToBeClickable(bookBtn));
    SeleniumUtils.jsClick(driver, bookBtn);
    wait.until(ExpectedConditions.urlContains("product-demo"));
  }

  /**
   * Method to fill out the booking form with the passed in info.
   * @param driver - Webdriver
   * @param products - List of products we want to tick to request a demo of
   * @param firstName - First name of test user
   * @param surname - Last name of test user
   * @param phoneNumber - Phone number of test user
   * @param email - Email of test user
   * @param company - Company of test user
   * @param marketing - Boolean to select if we want marketing or not
   */
  public static void enterBookingFormData(WebDriver driver, List<String> products, String firstName,
                                          String surname, String phoneNumber, String email, String company, Boolean marketing){
    //Loop and choose the correct products in the form
    for(String item : products){
      driver.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", item))).click();
    }

    driver.findElement(By.xpath(Locators.returnBookingXpath("bookingFirstNameXpath"))).sendKeys(firstName);
    driver.findElement(By.xpath(Locators.returnBookingXpath("bookingSurnameXpath"))).sendKeys(surname);
    driver.findElement(By.xpath(Locators.returnBookingXpath("bookingPhoneXpath"))).sendKeys(phoneNumber);
    driver.findElement(By.xpath(Locators.returnBookingXpath("bookingEmailXpath"))).sendKeys(email);
    driver.findElement(By.xpath(Locators.returnBookingXpath("bookingCompanyXpath"))).sendKeys(company);

    if(marketing){
      driver.findElement(By.xpath(Locators.returnBookingXpath("bookingNewsletterXpath"))).click();
    }
  }

  public static boolean bookDemoAndValidate(WebDriver driver, WebDriverWait wait){
    navigateToBookingPage(driver, wait);
    //Ideally below could be part of a test object we could mainatin or pass in but I didn't want to make this overkill
    // for a test we won't run anyway
    List<String> products = Arrays.asList("AccountancyManager", "BrightPay");
    enterBookingFormData(driver, products, "FirstName", "Surname", "123456789", "close995@gmail.com", "testCompany", true);
    //driver.findElement(By.xpath("//input[@value=\"Request a demo\"]"));     - Not wanting to spam the live system so this is commented out

    //Further code would be added here to check that the correct values are entered as per the test data e.g. pulling
    //data from a database where this is stored.

    return true; //We would return the boolean of the validate from the above comment
  }

}
