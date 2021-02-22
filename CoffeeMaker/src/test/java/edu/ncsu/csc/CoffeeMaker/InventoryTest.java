package edu.ncsu.csc.CoffeeMaker;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class InventoryTest {
    @Autowired
    private InventoryService inventoryService;

    @Before
    public void setup () {
        inventoryService.deleteAll();
        final Inventory ivt = inventoryService.getInventory();

        final Ingredient i1 = new Ingredient();
        final Ingredient i2 = new Ingredient();
        final Ingredient i3 = new Ingredient();
        final Ingredient i4 = new Ingredient();

        i1.setAmount( 500 );
        i1.setIngredient( "chocolate" );

        i2.setAmount( 500 );
        i2.setIngredient( "sugar" );

        i3.setAmount( 500 );
        i3.setIngredient( "milk" );

        i4.setAmount( 500 );
        i4.setIngredient( "coffee" );

        ivt.addIngredient( i1 );
        ivt.addIngredient( i2 );
        ivt.addIngredient( i3 );
        ivt.addIngredient( i4 );

        inventoryService.save( ivt );
    }

    @Test
    @Transactional
    public void testUpdateInventory () {

        Inventory ivt = inventoryService.getInventory();

        final Ingredient new_i1 = new Ingredient();
        new_i1.setAmount( 50 );
        new_i1.setIngredient( "chocolate" );
        ivt.addIngredient( new_i1 );

        final Ingredient new_i4 = new Ingredient();
        new_i4.setAmount( 10 );
        new_i4.setIngredient( "coffee" );
        ivt.addIngredient( new_i4 );

        final Ingredient new_i3 = new Ingredient();
        new_i3.setAmount( 25 );
        new_i3.setIngredient( "milk" );
        ivt.addIngredient( new_i3 );

        final Ingredient new_i2 = new Ingredient();
        new_i2.setAmount( 15 );
        new_i2.setIngredient( "sugar" );
        ivt.addIngredient( new_i2 );

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        int testAmount = 0;
        for ( int i = 0; i < ivt.getIngredients().size(); i++ ) {
            if ( ivt.getIngredients().get( i ).getIngredient().equals( "chocolate" ) ) {
                testAmount = ivt.getIngredients().get( i ).getAmount();
                assertEquals( 550, testAmount );
            }
            if ( ivt.getIngredients().get( i ).getIngredient().equals( "coffee" ) ) {
                testAmount = ivt.getIngredients().get( i ).getAmount();
                assertEquals( 510, testAmount );
            }
            if ( ivt.getIngredients().get( i ).getIngredient().equals( "milk" ) ) {
                testAmount = ivt.getIngredients().get( i ).getAmount();
                assertEquals( 525, testAmount );
            }
            if ( ivt.getIngredients().get( i ).getIngredient().equals( "sugar" ) ) {
                testAmount = ivt.getIngredients().get( i ).getAmount();
                assertEquals( 515, testAmount );
            }
        }

    }

    @Test
    @Transactional
    public void testEnoughIngredients () {
        Inventory ivt = inventoryService.getInventory();

        final Ingredient creamer = new Ingredient();
        creamer.setAmount( 0 );
        creamer.setIngredient( "creamer" );
        ivt.addIngredient( creamer );

        final Ingredient nutmeg = new Ingredient();
        nutmeg.setAmount( 5 );
        nutmeg.setIngredient( "nutmeg" );
        ivt.addIngredient( nutmeg );

        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        final Recipe r2 = new Recipe();
        r2.setName( "Latte" );
        r2.setPrice( 2 );
        r2.addIngredient( nutmeg );
        r2.addIngredient( creamer );
        r2.getIngredient().get( 0 ).setAmount( 1 );
        r2.getIngredient().get( 1 ).setAmount( 1 );
        inventoryService.save( r2 );

        ivt = inventoryService.getInventory();
        assertFalse( ivt.enoughIngredients( r2 ) );

    }

    @Test
    @Transactional
    public void testCheckIngredients () {

        final Inventory ivt = inventoryService.getInventory();

        // Test that a positive integer is valid
        assertEquals( 1, ivt.checkIngredient( "1" ) );

        // Test that a string or character is invalid
        try {
            ivt.checkIngredient( "a" );
        }
        catch ( final IllegalArgumentException e ) {

        }

        // Test that a negative integer is invalid
        try {
            ivt.checkIngredient( "-5" );
        }
        catch ( final IllegalArgumentException e ) {

        }
    }

    @Test
    @Transactional
    public void testUseIngredients () {

        Inventory ivt = inventoryService.getInventory();

        final Ingredient creamer = new Ingredient();
        creamer.setAmount( 5 );
        creamer.setIngredient( "creamer" );
        ivt.addIngredient( creamer );

        final Ingredient nutmeg = new Ingredient();
        nutmeg.setAmount( 5 );
        nutmeg.setIngredient( "nutmeg" );
        ivt.addIngredient( nutmeg );

        final Ingredient nutmeg2 = new Ingredient();
        nutmeg2.setAmount( 1 );
        nutmeg2.setIngredient( "nutmeg" );

        final Ingredient creamer2 = new Ingredient();
        creamer2.setAmount( 1 );
        creamer2.setIngredient( "creamer" );

        final Recipe r2 = new Recipe();
        r2.setName( "Latte" );
        r2.setPrice( 2 );
        r2.addIngredient( nutmeg2 );
        r2.addIngredient( creamer2 );
        inventoryService.save( r2 );

        ivt = inventoryService.getInventory();
        assertTrue( ivt.useIngredients( r2 ) );
        ivt = inventoryService.getInventory();

        int testAmount = 0;
        for ( int i = 0; i < ivt.getIngredients().size(); i++ ) {
            if ( ivt.getIngredients().get( i ).getIngredient().equals( "creamer" ) ) {
                testAmount = ivt.getIngredients().get( i ).getAmount();
                assertEquals( 4, testAmount );
            }
        }
        inventoryService.deleteAll();

    }
}

// Assert.assertEquals( "Adding to the inventory should result in
// correctly-updated values", 540, ivt.getMilk() );
// Assert.assertEquals( "Adding to the inventory should result in
// correctly-updated values", 530, ivt.getSugar() );
// Assert.assertEquals( "Adding to the inventory should result in
// correctly-updated values", 520,
// ivt.getChocolate() );
//
// try {
// ivt.addIngredients( 10, 20, 30, -40 );
// Assert.fail( "Trying to make an invalid update to the inventory should throw
// an exception" );
// }
// catch ( final Exception e ) {
// Assert.assertEquals( "Trying to add a negative value to the inventory should
// result in no updates", 550,
// ivt.getCoffee() );
// Assert.assertEquals( "Trying to add a negative value to the inventory should
// result in no updates", 540,
// ivt.getMilk() );
// Assert.assertEquals( "Trying to add a negative value to the inventory should
// result in no updates", 530,
// ivt.getSugar() );
// Assert.assertEquals( "Trying to add a negative value to the inventory should
// result in no updates", 520,
// ivt.getChocolate() );
// }
//
// }
//
// @Test
// @Transactional
// public void testConsumeInventory () {
// final Inventory i = inventoryService.getInventory();
//
// final Recipe recipe = new Recipe();
// recipe.setName( "Delicious Not-Coffee" );
// recipe.setChocolate( 10 );
// recipe.setMilk( 20 );
// recipe.setSugar( 5 );
// recipe.setCoffee( 1 );
//
// recipe.setPrice( 5 );
//
// i.useIngredients( recipe );
//
// /*
// * Make sure that all of the inventory fields are now properly updated
// */
//
// Assert.assertEquals( 490, i.getChocolate() );
// Assert.assertEquals( 480, i.getMilk() );
// Assert.assertEquals( 495, i.getSugar() );
// Assert.assertEquals( 499, i.getCoffee() );
// }
//
// }
