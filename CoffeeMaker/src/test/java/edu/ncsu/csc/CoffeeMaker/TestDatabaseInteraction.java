package edu.ncsu.csc.CoffeeMaker;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class TestDatabaseInteraction {

    @Autowired
    private RecipeService recipeService;

    @Test
    @Transactional
    public void testRecipes () {
        recipeService.deleteAll();

        final Recipe r = new Recipe();

        r.setName( "Special Drink" );

        r.setChocolate( 5 );
        r.setCoffee( 6 );
        r.setMilk( 7 );
        r.setSugar( 8 );

        r.setPrice( 2 );

        recipeService.save( r );

        final List<Recipe> dbRecipes = (List<Recipe>) recipeService.findAll();

        assertEquals( 1, dbRecipes.size() );

        final Recipe dbRecipe = dbRecipes.get( 0 );

        /*
         * Note we are _not_ using the `Recipe.equals(Object)` method here
         * because it only checks the name!
         */
        assertEquals( r.getName(), dbRecipe.getName() );
        assertEquals( r.getChocolate(), dbRecipe.getChocolate() );
        assertEquals( r.getMilk(), dbRecipe.getMilk() );
        assertEquals( r.getSugar(), dbRecipe.getSugar() );
        assertEquals( r.getPrice(), dbRecipe.getPrice() );

        final Recipe dbRecipeByName = recipeService.findByName( "Special Drink" );

        assertEquals( r.getChocolate(), dbRecipeByName.getChocolate() );

        dbRecipe.setPrice( 15 );
        dbRecipe.setSugar( 12 );
        recipeService.save( dbRecipe );

        assertEquals( 1, recipeService.count() );

        assertEquals( 15, (int) ( (Recipe) recipeService.findAll().get( 0 ) ).getPrice() );

    }

    @Test
    @Transactional
    public void testDelete () {
        recipeService.deleteAll();

        final Recipe r = new Recipe();

        r.setName( "coffee" );

        r.setChocolate( 5 );
        r.setCoffee( 6 );
        r.setMilk( 7 );
        r.setSugar( 8 );

        r.setPrice( 2 );

        recipeService.save( r );

        final List<Recipe> dbRecipes = (List<Recipe>) recipeService.findAll();

        assertEquals( 1, dbRecipes.size() );

        final Recipe dbRecipe = dbRecipes.get( 0 );

        /*
         * Note we are _not_ using the `Recipe.equals(Object)` method here
         * because it only checks the name!
         */
        assertEquals( r.getName(), dbRecipe.getName() );
        assertEquals( r.getChocolate(), dbRecipe.getChocolate() );
        assertEquals( r.getMilk(), dbRecipe.getMilk() );
        assertEquals( r.getSugar(), dbRecipe.getSugar() );
        assertEquals( r.getPrice(), dbRecipe.getPrice() );

        final Recipe dbRecipeByName = recipeService.findByName( "coffee" );

        assertEquals( r.getChocolate(), dbRecipeByName.getChocolate() );

        dbRecipe.setPrice( 15 );
        dbRecipe.setSugar( 12 );
        recipeService.save( dbRecipe );

        assertEquals( 1, recipeService.count() );

        assertEquals( 15, (int) ( (Recipe) recipeService.findAll().get( 0 ) ).getPrice() );

        recipeService.delete( dbRecipeByName );

        assertEquals( 0, recipeService.count() );

    }
}
