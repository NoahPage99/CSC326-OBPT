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

public class DeleteRecipeWebStepDefs extends CucumberTest {

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

    @Given ( "^there is a recipe called(.+)$" )
    public void insertRecipe ( final String recipeName ) {
        DBUtils.resetDB( dataSource );
        baseUrl = "http://localhost:8080";
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
        driver.get( baseUrl + "" );

        driver.findElement( By.linkText( "Add a Recipe" ) ).click();

        driver.findElement( By.name( "name" ) ).clear();
        driver.findElement( By.name( "name" ) ).sendKeys( recipeName );
        driver.findElement( By.name( "price" ) ).clear();
        driver.findElement( By.name( "price" ) ).sendKeys( "1" );
        driver.findElement( By.name( "coffee" ) ).clear();
        driver.findElement( By.name( "coffee" ) ).sendKeys( "1" );
        driver.findElement( By.name( "milk" ) ).clear();
        driver.findElement( By.name( "milk" ) ).sendKeys( "1" );
        driver.findElement( By.name( "sugar" ) ).clear();
        driver.findElement( By.name( "sugar" ) ).sendKeys( "1" );
        driver.findElement( By.name( "chocolate" ) ).clear();
        driver.findElement( By.name( "chocolate" ) ).sendKeys( "1" );

        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

        driver.findElement( By.linkText( "Home" ) ).click();
    }

    @Given ( "^there are recipes called (.+) and (.+)$" )
    public void insertRecipes ( final String name1, final String name2 ) {
        DBUtils.resetDB( dataSource );
        baseUrl = "http://localhost:8080";
        driver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
        driver.get( baseUrl + "" );

        driver.findElement( By.linkText( "Add a Recipe" ) ).click();

        driver.findElement( By.name( "name" ) ).clear();
        driver.findElement( By.name( "name" ) ).sendKeys( name1 );
        driver.findElement( By.name( "price" ) ).clear();
        driver.findElement( By.name( "price" ) ).sendKeys( "1" );
        driver.findElement( By.name( "coffee" ) ).clear();
        driver.findElement( By.name( "coffee" ) ).sendKeys( "1" );
        driver.findElement( By.name( "milk" ) ).clear();
        driver.findElement( By.name( "milk" ) ).sendKeys( "1" );
        driver.findElement( By.name( "sugar" ) ).clear();
        driver.findElement( By.name( "sugar" ) ).sendKeys( "1" );
        driver.findElement( By.name( "chocolate" ) ).clear();
        driver.findElement( By.name( "chocolate" ) ).sendKeys( "1" );

        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

        driver.findElement( By.name( "name" ) ).clear();
        driver.findElement( By.name( "name" ) ).sendKeys( name2 );
        driver.findElement( By.name( "price" ) ).clear();
        driver.findElement( By.name( "price" ) ).sendKeys( "2" );
        driver.findElement( By.name( "coffee" ) ).clear();
        driver.findElement( By.name( "coffee" ) ).sendKeys( "1" );
        driver.findElement( By.name( "milk" ) ).clear();
        driver.findElement( By.name( "milk" ) ).sendKeys( "1" );
        driver.findElement( By.name( "sugar" ) ).clear();
        driver.findElement( By.name( "sugar" ) ).sendKeys( "1" );
        driver.findElement( By.name( "chocolate" ) ).clear();
        driver.findElement( By.name( "chocolate" ) ).sendKeys( "1" );

        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

        driver.findElement( By.linkText( "Home" ) ).click();
    }

    @When ( "^I delete the recipe with name (.+)$" )
    public void deleteRecipe ( final String recipeName ) {

        driver.findElement( By.linkText( "Delete Recipe" ) ).click();
        driver.findElement( By.xpath( "//input[@value=\"" + recipeName + "\"]" ) ).click();
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

    }

    @When ( "^I delete the recipes with delete all option$" )
    public void deleteRecipes () {

        driver.findElement( By.linkText( "Delete Recipe" ) ).click();
        driver.findElement( By.cssSelector( "input[type=\"checkbox\"]" ) ).click();
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();

    }

    @Then ( "^the recipe is deleted from the system$" )
    public void confirmDeleted () {
        assertTextPresent( "Recipe deleted successfully", driver );
        driver.findElement( By.linkText( "Home" ) ).click();
    }

    @Then ( "^they are deleted$" )
    public void confirmDeletedAll () {
        assertTextPresent( "Recipe deleted successfully", driver );
        driver.findElement( By.linkText( "Home" ) ).click();
    }

}
