package edu.ncsu.csc.CoffeeMaker.cucumber.web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith ( Cucumber.class )
@CucumberOptions ( features = "src/test/resources/web", glue = { "edu.ncsu.csc.CoffeeMaker.cucumber" } )
public class ITRunner {

    @BeforeClass
    public static void setUp () {
        CucumberTest.setup();
    }

    @AfterClass
    public static void tearDown () {
    }

}
