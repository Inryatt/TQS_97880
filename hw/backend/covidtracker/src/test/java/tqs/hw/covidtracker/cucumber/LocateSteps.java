package tqs.hw.covidtracker.cucumber;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebElement;

import static java.lang.Thread.sleep;

public class LocateSteps {


    private WebDriver driver = WebDriverManager.firefoxdriver().create();
    //private WebDriver driver = WebDriverManager.firefoxdriver().create();

    HashMap<String,Integer> vars = new HashMap<>();
    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        System.err.println(url);
        driver.get(url);
    }

    @And("I click {string}")
    public void i_click(String string) {
        driver.findElement(By.linkText(string)).click();
    }



    @Then("I should see the text {string}")
    public void i_should_see_the_text(String string) {
        try {
            driver.findElement(
                    By.xpath("//*[contains(text(), '" + string + "')]"));
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + string + "\" not available in results");
        }

    }
    @And("I write country {string}")
    public void i_write_country(String string) {
        WebElement countryBar = driver.findElement(By.id("country_name"));
        countryBar.click();
        countryBar.sendKeys(string);

    }


    @And("I press Enter")
    public void i_press_enter() throws InterruptedException {
        WebElement countryBar = driver.findElement(By.id("country_name"));
        countryBar.sendKeys(Keys.ENTER);
        sleep(3);

    }



}
