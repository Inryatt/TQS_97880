package tqs.lab4.ex3;

// Generated by Selenium IDE and fixed/cleaned by yours truly

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// I don't understand this. WIP

@ExtendWith(SeleniumJupiter.class)
public class CleanerSeleniumTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;

  private static String PAGE_URL="https://www.toptal.com";


  @FindBy(how = How.LINK_TEXT, using = "APPLY AS A DEVELOPER")
  private WebElement developerApplyButton;


   //Constructor
   public CleanerSeleniumTest(WebDriver driver){
    this.driver=driver;
    driver.get(PAGE_URL);
    //Initialise Elements
    PageFactory.initElements(driver, this);
}


  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void untitled() {
    driver.get(PAGE_URL);
    driver.manage().window().setSize(new Dimension(960, 1055));

   
    driver.findElement(By.name("fromPort")).click();
    {
      WebElement dropdown = driver.findElement(By.name("fromPort"));
      dropdown.findElement(By.xpath("//option[. = 'Philadelphia']")).click();
    }
    driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(2)")).click();
    driver.findElement(By.name("toPort")).click();
    {
      WebElement dropdown = driver.findElement(By.name("toPort"));
      dropdown.findElement(By.xpath("//option[. = 'Rome']")).click();
    }
    vars.put("dest", driver.findElement(By.name("toPort")).getAttribute("value"));
    assertTrue(driver.findElement(By.name("toPort")).getText().contains("Rome"));
    driver.findElement(By.cssSelector(".btn-primary")).click();
    driver.findElement(By.cssSelector("tr:nth-child(5) .btn")).click();
    js.executeScript("window.scrollTo(0,195)");
    driver.findElement(By.cssSelector(".btn-primary")).click();
    assertEquals(driver.getTitle(),"BlazeDemo Confirmation");
  }
}
