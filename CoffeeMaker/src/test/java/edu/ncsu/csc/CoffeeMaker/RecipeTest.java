package edu.ncsu.csc.CoffeeMaker;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class RecipeTest {

    @Autowired
    private RecipeService service;

    @Before
    public void setup () {
        service.deleteAll();
    }

    @Test
    @Transactional
    public void testAddRecipe () {

        final Recipe r1 = new Recipe();
        // r1.setId(1L);

        final Ingredient i = new Ingredient( "Milk", 1 );

        r1.setName( "Black Coffee" );
        r1.setPrice( 1 );
        r1.addIngredient( i );

        service.save( r1 );

        final List<Recipe> recipes = (List<Recipe>) service.findAll();

        final Recipe r = recipes.get( 0 );

        assertEquals( r1.getIngredient(), r.getIngredient() );
        assertEquals( r1.getId(), r.getId() );
        assertEquals( r1.getName(), r.getName() );
        assertEquals( r1.getPrice(), r.getPrice() );

        final Recipe r2 = new Recipe();
        r2.setName( "Mocha" );
        r2.setPrice( 1 );

        final Ingredient i2 = new Ingredient( "Chocolate", 1 );

        r2.addIngredient( i2 );
        r1.updateRecipe( r2 );

        service.save( r1 );

        assertEquals( r2.getIngredient(), r.getIngredient() );
        assertEquals( r2.getName(), r.getName() );
        assertEquals( r2.getPrice(), r.getPrice() );
        assertTrue( r1.equals( r1 ) );
        final Recipe r3 = new Recipe();
        assertFalse( r1.equals( r3 ) );
        r1.hashCode();
        assertEquals( r2.toString(), "Mocha" );
    }

    // @Test
    // @Transactional
    // public void testNoRecipes () {
    // Assert.assertEquals( "There should be no Recipes in the CoffeeMaker", 0,
    // service.findAll().size() );
    //
    // final Recipe r1 = new Recipe();
    // r1.setName( "Tasty Drink" );
    // r1.setPrice( 12 );
    // r1.setCoffee( -12 );
    // r1.setMilk( 0 );
    // r1.setSugar( 0 );
    // r1.setChocolate( 0 );
    //
    // final Recipe r2 = new Recipe();
    // r2.setName( "Mocha" );
    // r2.setPrice( 1 );
    // r2.setCoffee( 1 );
    // r2.setMilk( 1 );
    // r2.setSugar( 1 );
    // r2.setChocolate( 1 );
    //
    // final List<Recipe> recipes = new ArrayList<Recipe>();
    //
    // recipes.add( r1 );
    // recipes.add( r2 );
    //
    // try {
    // service.saveAll( recipes );
    // Assert.assertEquals(
    // "Trying to save a collection of elements where one is invalid should
    // result in neither getting saved",
    // 0, service.count() );
    // }
    // catch ( final Exception e ) {
    // Assert.assertTrue( e instanceof ConstraintViolationException );
    // }
    //
    // }

}
