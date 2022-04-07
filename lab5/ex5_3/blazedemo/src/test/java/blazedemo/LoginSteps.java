/*
 * (C) Copyright 2020 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package blazedemo;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import static java.lang.Thread.sleep;


public class LoginSteps {

    private WebDriver driver = WebDriverManager.firefoxdriver().create();;
    @FindBy(how = How.CSS, using = "input[class='btn btn-small']")
    List<WebElement> pickFlightButton;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver.get(url);
    }

    @When("I select origin {string} and destination  {string}")
    public void i_select_origin_and_destination(String origin, String dest) {
        // Write code here that turns the phrase above into concrete actions
        driver.findElement(By.name("fromPort")).click();
        {
            Select dropdown = new Select(driver.findElement(By.name("fromPort")));
            dropdown.selectByVisibleText(origin);
        }
        driver.findElement(By.name("toPort")).click();
        {
            Select dropdown = new Select(driver.findElement(By.name("toPort")));
            dropdown.selectByVisibleText(dest);
        }
    }

    @And("I click Submit")
    public void i_click_Submit() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("I should see the message {string}")
    public void i_should_see_the_message(String string) {
        try {
        driver.findElement(
                By.xpath("//*[contains(text(), '" + string + "')]"));
    } catch (NoSuchElementException e) {
        throw new AssertionError(
                "\"" + string + "\" not available in results");
    }
    }
    @Then("I select flight {int}")
    public void i_select_flight(Integer int1) throws InterruptedException {
//        pickFlightButton.get(int1).click();
        sleep(3);
        //driver.findElement(By.xpath("(//input[@value='Choose This Flight'])[2]")).click();
        driver.findElement(By.cssSelector("tr:nth-child(2) .btn")).click();

    }
    @Then("I input {string} {string}")
    public void i_input(String field,String string) {
        driver.findElement(By.id(field)).click();
        driver.findElement(By.id(field)).sendKeys(string);
    }

}
