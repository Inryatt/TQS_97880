package tqs.lab4.ex1;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;


class HelloWorldChromeJupiterTest {

    Logger log = Logger.getLogger(HelloWorldChromeJupiterTest.class.getName());

    private WebDriver driver; 

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup(); 
    }

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver(); 
    }

    @Test
    void test() {
        // Exercise
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl); 
        String title = driver.getTitle(); 
        log.debug("The title of " +sutUrl+"  is "+ title); 

        // Verify
        assertEquals(title,"Hands-On Selenium WebDriver with Java"); 
    }

    @AfterEach
    void teardown() {
        driver.quit(); 
    }

}
