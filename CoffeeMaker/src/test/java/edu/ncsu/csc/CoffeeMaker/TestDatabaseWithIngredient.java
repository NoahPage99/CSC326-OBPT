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
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class TestDatabaseWithIngredient {

    @Autowired
    private IngredientService IService;

    @Test
    @Transactional
    public void testIngredient () {

        final Ingredient i = new Ingredient();

        i.setId( 1L );
        i.setIngredient( "Milk" );
        i.setAmount( 3 );

        IService.save( i );
        final List<Ingredient> ingredients = (List<Ingredient>) IService.findAll();

        assertEquals( 1, ingredients.size() );

        final Ingredient ingredient = ingredients.get( 0 );

        assertEquals( i.getIngredient(), ingredient.getIngredient() );
        assertEquals( i.getAmount(), ingredient.getAmount() );

    }

    @Test
    @Transactional
    public void testIngredientContructor () {

        final Ingredient i = new Ingredient( "Pumpkin", 2 );

        IService.save( i );

        final List<Ingredient> ingredients = (List<Ingredient>) IService.findAll();

        assertEquals( 1, ingredients.size() );

        final Ingredient ingredient = ingredients.get( 0 );

        assertEquals( i.getId(), ingredient.getId() );
        assertEquals( i.getIngredient(), ingredient.getIngredient() );
        assertEquals( i.getAmount(), ingredient.getAmount() );

        assertEquals( i.toString(), "Ingredient [ingredient=Pumpkin, amount=2]" );
        assertEquals( ingredient.toString(), "Ingredient [ingredient=Pumpkin, amount=2]" );
    }

}
