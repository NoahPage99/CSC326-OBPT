package edu.ncsu.csc.CoffeeMaker.cucumber.web;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ncsu.csc.CoffeeMaker.common.DBUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddIngredientWebStepDefs extends CucumberTest {

    /** The URL for CoffeeMaker - change as needed */
    private String     baseUrl;
    // private final StringBuffer verificationErrors = new StringBuffer();

    @Autowired
    private DataSource dataSource;

    /**
     * Asserts that the text is on the page
     *
     * @param text
     *            text to check
     * @param driver
     *            web driver
     */
    public void assertTextPresent ( final String text, final WebDriver driver ) {
        final List<WebElement> list = driver.findElements( By.xpath( "//*[contains(text(),'" + text + "')]" ) );
        assertTrue( "Text not found!", list.size() > 0 );
    }

    @Given ( "^I have navigated to the Add Ingredient screen and the inventory is empty$" )
    public void navigateAndClear () {
        DBUtils.resetDB( dataSource );
        baseUrl = "http://localhost:8080";
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
        driver.get( baseUrl + "" );

        driver.findElement( By.linkText( "Add an Ingredient" ) ).click();
    }

    @When ( "^I add an ingredient named (.+) with amount (.+)$" )
    public void addIngredient ( final String name, final String amt ) {
        driver.findElement( By.name( "name" ) ).clear();
        driver.findElement( By.name( "name" ) ).sendKeys( name );
        driver.findElement( By.name( "amount" ) ).clear();
        driver.findElement( By.name( "amount" ) ).sendKeys( amt );

        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();
    }

    @Then ( "^the ingredient is added to the system$" )
    public void addedSuccessfully () {
        assertTextPresent( "Ingredient Added", driver );

        driver.findElement( By.linkText( "Home" ) ).click();
    }

    @Then ( "^an error message is displayed$" )
    public void addedUnsuccessfully () {
        assertTextPresent( "Error Adding Ingredient", driver );

        driver.findElement( By.linkText( "Home" ) ).click();
    }

}
