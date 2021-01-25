package edu.ncsu.csc.CoffeeMaker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
        final Inventory ivt = inventoryService.getInventory();

        ivt.setChocolate( 500 );
        ivt.setCoffee( 500 );
        ivt.setMilk( 500 );
        ivt.setSugar( 500 );

        inventoryService.save( ivt );
    }

    @Test
    @Transactional
    public void testUpdateInventory () {

        Inventory ivt = inventoryService.getInventory();

        ivt.addIngredients( 50, 40, 30, 20 );

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        Assert.assertEquals( "Adding to the inventory should result in correctly-updated values", 550,
                ivt.getCoffee() );
        Assert.assertEquals( "Adding to the inventory should result in correctly-updated values", 540, ivt.getMilk() );
        Assert.assertEquals( "Adding to the inventory should result in correctly-updated values", 530, ivt.getSugar() );
        Assert.assertEquals( "Adding to the inventory should result in correctly-updated values", 520,
                ivt.getChocolate() );

        try {
            ivt.addIngredients( 10, 20, 30, -40 );
            Assert.fail( "Trying to make an invalid update to the inventory should throw an exception" );
        }
        catch ( final Exception e ) {
            Assert.assertEquals( "Trying to add a negative value to the inventory should result in no updates", 550,
                    ivt.getCoffee() );
            Assert.assertEquals( "Trying to add a negative value to the inventory should result in no updates", 540,
                    ivt.getMilk() );
            Assert.assertEquals( "Trying to add a negative value to the inventory should result in no updates", 530,
                    ivt.getSugar() );
            Assert.assertEquals( "Trying to add a negative value to the inventory should result in no updates", 520,
                    ivt.getChocolate() );
        }

    }

    @Test
    @Transactional
    public void testConsumeInventory () {
        final Inventory i = inventoryService.getInventory();

        final Recipe recipe = new Recipe();
        recipe.setName( "Delicious Not-Coffee" );
        recipe.setChocolate( 10 );
        recipe.setMilk( 20 );
        recipe.setSugar( 5 );
        recipe.setCoffee( 1 );

        recipe.setPrice( 5 );

        i.useIngredients( recipe );

        /*
         * Make sure that all of the inventory fields are now properly updated
         */

        Assert.assertEquals( 490, i.getChocolate() );
        Assert.assertEquals( 480, i.getMilk() );
        Assert.assertEquals( 495, i.getSugar() );
        Assert.assertEquals( 499, i.getCoffee() );
    }

}
