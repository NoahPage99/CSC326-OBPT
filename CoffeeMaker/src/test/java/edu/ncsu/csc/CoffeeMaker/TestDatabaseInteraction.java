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

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
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
        // r1.setId(1L);

        final Ingredient i = new Ingredient( "Milk", 1 );
        final Ingredient i2 = new Ingredient( "Chocolate", 1 );

        r.setName( "Black Coffee" );
        r.setPrice( 1 );
        r.addIngredient( i );
        r.addIngredient( i2 );

        recipeService.save( r );

        final List<Recipe> dbRecipes = (List<Recipe>) recipeService.findAll();

        assertEquals( 1, dbRecipes.size() );

        final Recipe dbRecipe = dbRecipes.get( 0 );

        /*
         * Note we are _not_ using the `Recipe.equals(Object)` method here
         * because it only checks the name!
         */
        assertEquals( r.getName(), dbRecipe.getName() );
        assertEquals( r.getName(), "Black Coffee" );
        assertEquals( r.getIngredient(), dbRecipe.getIngredient() );
        assertEquals( r.getId(), dbRecipe.getId() );
        assertEquals( r.getPrice(), dbRecipe.getPrice() );

        final Recipe dbRecipeByName = recipeService.findByName( "Black Coffee" );

        assertEquals( r.getIngredient(), dbRecipeByName.getIngredient() );

        dbRecipe.setPrice( 15 );

        recipeService.save( dbRecipe );

        assertEquals( 1, recipeService.count() );

        assertEquals( 15, (int) ( (Recipe) recipeService.findAll().get( 0 ) ).getPrice() );

    }

    @Test
    @Transactional
    public void testDelete () {
        recipeService.deleteAll();

        final Recipe r = new Recipe();
        // r1.setId(1L);

        final Ingredient i = new Ingredient( "Milk", 1 );
        final Ingredient i2 = new Ingredient( "Chocolate", 1 );

        r.setName( "Black Coffee" );
        r.setPrice( 1 );
        r.addIngredient( i );
        r.addIngredient( i2 );

        recipeService.save( r );

        final List<Recipe> dbRecipes = (List<Recipe>) recipeService.findAll();

        assertEquals( 1, dbRecipes.size() );

        final Recipe dbRecipe = dbRecipes.get( 0 );

        /*
         * Note we are _not_ using the `Recipe.equals(Object)` method here
         * because it only checks the name!
         */
        assertEquals( r.getName(), dbRecipe.getName() );
        assertEquals( r.getName(), "Black Coffee" );
        assertEquals( r.getIngredient(), dbRecipe.getIngredient() );
        assertEquals( r.getId(), dbRecipe.getId() );
        assertEquals( r.getPrice(), dbRecipe.getPrice() );

        final Recipe dbRecipeByName = recipeService.findByName( "Black Coffee" );

        assertEquals( r.getIngredient(), dbRecipeByName.getIngredient() );

        dbRecipe.setPrice( 15 );

        recipeService.save( dbRecipe );

        assertEquals( 1, recipeService.count() );

        assertEquals( 15, (int) ( (Recipe) recipeService.findAll().get( 0 ) ).getPrice() );

        recipeService.delete( dbRecipeByName );

        assertEquals( 0, recipeService.count() );

    }

}
