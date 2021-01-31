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

public class UpdateInventoryWebStepDefs extends CucumberTest {

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

    @Given ( "^the inventory update screen is open$" )
    public void openUpdateScreen () {
        DBUtils.resetDB( dataSource );
        baseUrl = "http://localhost:8080";
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
        driver.get( baseUrl + "" );
        driver.findElement( By.linkText( "Update Inventory" ) ).click();
    }

    @When ( "^I add (\\d+) of (.+)$" )
    public void addIngredient ( final Integer amt, final String ing ) {
        if ( ing.equals( "coffee" ) ) {

            driver.findElement( By.name( "coffee" ) ).clear();
            driver.findElement( By.name( "coffee" ) ).sendKeys( amt.toString() );
            driver.findElement( By.name( "milk" ) ).clear();
            driver.findElement( By.name( "milk" ) ).sendKeys( "0" );
            driver.findElement( By.name( "sugar" ) ).clear();
            driver.findElement( By.name( "sugar" ) ).sendKeys( "0" );
            driver.findElement( By.name( "chocolate" ) ).clear();
            driver.findElement( By.name( "chocolate" ) ).sendKeys( "0" );

        }
        else if ( ing.equals( "milk" ) ) {

            driver.findElement( By.name( "coffee" ) ).clear();
            driver.findElement( By.name( "coffee" ) ).sendKeys( "0" );
            driver.findElement( By.name( "milk" ) ).clear();
            driver.findElement( By.name( "milk" ) ).sendKeys( amt.toString() );
            driver.findElement( By.name( "sugar" ) ).clear();
            driver.findElement( By.name( "sugar" ) ).sendKeys( "0" );
            driver.findElement( By.name( "chocolate" ) ).clear();
            driver.findElement( By.name( "chocolate" ) ).sendKeys( "0" );

        }
        else if ( ing.equals( "sugar" ) ) {

            driver.findElement( By.name( "coffee" ) ).clear();
            driver.findElement( By.name( "coffee" ) ).sendKeys( "0" );
            driver.findElement( By.name( "milk" ) ).clear();
            driver.findElement( By.name( "milk" ) ).sendKeys( "0" );
            driver.findElement( By.name( "sugar" ) ).clear();
            driver.findElement( By.name( "sugar" ) ).sendKeys( amt.toString() );
            driver.findElement( By.name( "chocolate" ) ).clear();
            driver.findElement( By.name( "chocolate" ) ).sendKeys( "0" );

        }
        else if ( ing.equals( "chocolate" ) ) {

            driver.findElement( By.name( "coffee" ) ).clear();
            driver.findElement( By.name( "coffee" ) ).sendKeys( "0" );
            driver.findElement( By.name( "milk" ) ).clear();
            driver.findElement( By.name( "milk" ) ).sendKeys( "0" );
            driver.findElement( By.name( "sugar" ) ).clear();
            driver.findElement( By.name( "sugar" ) ).sendKeys( "0" );
            driver.findElement( By.name( "chocolate" ) ).clear();
            driver.findElement( By.name( "chocolate" ) ).sendKeys( amt.toString() );

        }

        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

    }

    @When ( "^I attempt to add (-?\\d+) of (.+)$" )
    public void addInvalidIngredient ( final Integer amt, final String ing ) {
        if ( ing.equals( "coffee" ) ) {

            driver.findElement( By.name( "coffee" ) ).clear();
            driver.findElement( By.name( "coffee" ) ).sendKeys( amt.toString() );
            driver.findElement( By.name( "milk" ) ).clear();
            driver.findElement( By.name( "milk" ) ).sendKeys( "0" );
            driver.findElement( By.name( "sugar" ) ).clear();
            driver.findElement( By.name( "sugar" ) ).sendKeys( "0" );
            driver.findElement( By.name( "chocolate" ) ).clear();
            driver.findElement( By.name( "chocolate" ) ).sendKeys( "0" );

        }
        else if ( ing.equals( "milk" ) ) {

            driver.findElement( By.name( "coffee" ) ).clear();
            driver.findElement( By.name( "coffee" ) ).sendKeys( "0" );
            driver.findElement( By.name( "milk" ) ).clear();
            driver.findElement( By.name( "milk" ) ).sendKeys( amt.toString() );
            driver.findElement( By.name( "sugar" ) ).clear();
            driver.findElement( By.name( "sugar" ) ).sendKeys( "0" );
            driver.findElement( By.name( "chocolate" ) ).clear();
            driver.findElement( By.name( "chocolate" ) ).sendKeys( "0" );

        }
        else if ( ing.equals( "sugar" ) ) {

            driver.findElement( By.name( "coffee" ) ).clear();
            driver.findElement( By.name( "coffee" ) ).sendKeys( "0" );
            driver.findElement( By.name( "milk" ) ).clear();
            driver.findElement( By.name( "milk" ) ).sendKeys( "0" );
            driver.findElement( By.name( "sugar" ) ).clear();
            driver.findElement( By.name( "sugar" ) ).sendKeys( amt.toString() );
            driver.findElement( By.name( "chocolate" ) ).clear();
            driver.findElement( By.name( "chocolate" ) ).sendKeys( "0" );

        }
        else if ( ing.equals( "chocolate" ) ) {

            driver.findElement( By.name( "coffee" ) ).clear();
            driver.findElement( By.name( "coffee" ) ).sendKeys( "0" );
            driver.findElement( By.name( "milk" ) ).clear();
            driver.findElement( By.name( "milk" ) ).sendKeys( "0" );
            driver.findElement( By.name( "sugar" ) ).clear();
            driver.findElement( By.name( "sugar" ) ).sendKeys( "0" );
            driver.findElement( By.name( "chocolate" ) ).clear();
            driver.findElement( By.name( "chocolate" ) ).sendKeys( amt.toString() );

        }

        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

    }

    @Then ( "^the inventory is successfully updated$" )
    public void addedSuccessfully () {
        // Make sure the proper message was displayed.
        assertTextPresent( "Inventory Successfully Updated", driver );

        driver.findElement( By.linkText( "Home" ) ).click();
    }

    @Then ( "^the inventory is not updated$" )
    public void notAddedSuccessfully () {
        // Make sure the proper message was displayed.
        assertTextPresent( "Error while updating inventory", driver );

        driver.findElement( By.linkText( "Home" ) ).click();
    }

}
