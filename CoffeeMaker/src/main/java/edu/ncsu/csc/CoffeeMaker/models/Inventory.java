package edu.ncsu.csc.CoffeeMaker.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Inventory for the coffee maker. Inventory is tied to the database using
 * Hibernate libraries. See InventoryRepository and InventoryService for the
 * other two pieces used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Inventory extends DomainObject {

    /** id for inventory entry */
    @Id
    @GeneratedValue
    private Long                   id;

    /** List of ingredients **/
    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private final List<Ingredient> ingredients;

    /**
     * Empty constructor for Hibernate
     */
    public Inventory () {
        this.ingredients = new LinkedList<Ingredient>();
        // Intentionally empty so that Hibernate can instantiate
        // Inventory object.
    }

    /**
     * Returns the ID of the entry in the DB
     *
     * @return long
     */
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Inventory (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Add the number of chocolate units in the inventory to the current amount
     * of chocolate units.
     *
     * @param chocolate
     *            amount of chocolate
     * @return checked amount of chocolate
     * @throws IllegalArgumentException
     *             if the parameter isn't a positive integer
     */
    public int checkChocolate ( final String chocolate ) throws IllegalArgumentException {
        int amtChocolate = 0;
        try {
            amtChocolate = Integer.parseInt( chocolate );
        }
        catch ( final NumberFormatException e ) {
            throw new IllegalArgumentException( "Units of chocolate must be a positive integer" );
        }
        if ( amtChocolate < 0 ) {
            throw new IllegalArgumentException( "Units of chocolate must be a positive integer" );
        }

        return amtChocolate;
    }

    /**
     * Add the number of coffee units in the inventory to the current amount of
     * coffee units.
     *
     * @param coffee
     *            amount of coffee
     * @return checked amount of coffee
     * @throws IllegalArgumentException
     *             if the parameter isn't a positive integer
     */
    public int checkCoffee ( final String coffee ) throws IllegalArgumentException {
        int amtCoffee = 0;
        try {
            amtCoffee = Integer.parseInt( coffee );
        }
        catch ( final NumberFormatException e ) {
            throw new IllegalArgumentException( "Units of coffee must be a positive integer" );
        }
        if ( amtCoffee < 0 ) {
            throw new IllegalArgumentException( "Units of coffee must be a positive integer" );
        }

        return amtCoffee;
    }

    /**
     * Add the number of milk units in the inventory to the current amount of
     * milk units.
     *
     * @param milk
     *            amount of milk
     * @return checked amount of milk
     * @throws IllegalArgumentException
     *             if the parameter isn't a positive integer
     */
    public int checkMilk ( final String milk ) throws IllegalArgumentException {
        int amtMilk = 0;
        try {
            amtMilk = Integer.parseInt( milk );
        }
        catch ( final NumberFormatException e ) {
            throw new IllegalArgumentException( "Units of milk must be a positive integer" );
        }
        if ( amtMilk < 0 ) {
            throw new IllegalArgumentException( "Units of milk must be a positive integer" );
        }

        return amtMilk;
    }

    /**
     * Add the number of sugar units in the inventory to the current amount of
     * sugar units.
     *
     * @param sugar
     *            amount of sugar
     * @return checked amount of sugar
     * @throws IllegalArgumentException
     *             if the parameter isn't a positive integer
     */
    public int checkSugar ( final String sugar ) throws IllegalArgumentException {
        int amtSugar = 0;
        try {
            amtSugar = Integer.parseInt( sugar );
        }
        catch ( final NumberFormatException e ) {
            throw new IllegalArgumentException( "Units of sugar must be a positive integer" );
        }
        if ( amtSugar < 0 ) {
            throw new IllegalArgumentException( "Units of sugar must be a positive integer" );
        }

        return amtSugar;
    }

    /**
     * Returns true if there are enough ingredients to make the beverage.
     *
     * @param r
     *            recipe to check if there are enough ingredients
     * @return true if enough ingredients to make the beverage
     */
    public boolean enoughIngredients ( final Recipe r ) {
        final List<Ingredient> ing = r.getIngredient();
        for ( int i = 0; i < ing.size(); i++ ) {
            for ( int j = 0; j < ingredients.size(); j++ ) {
                if ( ing.get( i ).getIngredient().equals( ingredients.get( j ).getIngredient() ) ) {
                    if ( ingredients.get( j ).getAmount() - ing.get( i ).getAmount() < 0 ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Removes the ingredients used to make the specified recipe. Assumes that
     * the user has checked that there are enough ingredients to make
     *
     * @param r
     *            recipe to make
     * @return true if recipe is made.
     */
    public boolean useIngredients ( final Recipe r ) {
        if ( enoughIngredients( r ) ) {
            final List<Ingredient> ing = r.getIngredient();
            for ( int i = 0; i < ing.size(); i++ ) {
                for ( int j = 0; j < ingredients.size(); j++ ) {
                    if ( ing.get( i ).getIngredient().equals( ingredients.get( j ).getIngredient() ) ) {
                        ingredients.get( j ).setAmount( ingredients.get( j ).getAmount() - ing.get( i ).getAmount() );
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Updates amount of an ingredient in the inventory
     *
     * @param ing
     *            the ingredient to update (the amount value of the Ingredient
     *            is the amount to add)
     */
    public boolean addIngredientAmount ( final Ingredient ing ) {
        for ( int i = 0; i < ingredients.size(); i++ ) {
            if ( ing.getIngredient().equals( ingredients.get( i ).getIngredient() ) ) {
                ingredients.get( i ).setAmount( ing.getAmount() + ingredients.get( i ).getAmount() );
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an Ingredient to the Inventory
     *
     * @param ing
     *            the ingredient to add
     * @return false if an ingredient with the same name is already in the
     *         system (it is not added), true if not (it is added)
     */
    public boolean addIngredient ( final Ingredient ing ) {
        for ( int i = 0; i < ingredients.size(); i++ ) {
            if ( ing.getIngredient().equals( ingredients.get( i ).getIngredient() ) ) {
                return false;
            }
        }
        ingredients.add( ing );
        return true;
    }

    @Override
    public String toString () {
        return "Inventory [ingredients=" + ingredients + "]";
    }

}
