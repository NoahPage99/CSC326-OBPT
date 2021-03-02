package edu.ncsu.csc.CoffeeMaker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class GenerateIngredients {

    @Autowired
    private IngredientService ingredientService;

    @Test
    @Transactional
    public void testCreateIngredients () {

        final int count = (int) ingredientService.count();

        assertEquals( 0, count );

        final Ingredient i1 = new Ingredient( "Coffee", 5 );

        ingredientService.save( i1 );

        final Ingredient i2 = new Ingredient( "Milk", 3 );

        ingredientService.save( i2 );

        assertEquals( 2, ingredientService.count() );

    }
}
