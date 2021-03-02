package edu.ncsu.csc.CoffeeMaker.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

/**
 * Recipe for the coffee maker. Recipe is tied to the database using Hibernate
 * libraries. See RecipeRepository and RecipeService for the other two pieces
 * used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Recipe extends DomainObject {

    /** Recipe id */
    @Id
    @GeneratedValue
    private Long             id;

    /** Recipe name */
    private String           name;

    /** Recipe price */
    @Min ( 0 )
    private Integer          price;

    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<Ingredient> ingredient;

    /**
     * Creates a default recipe for the coffee maker.
     */
    public Recipe () {

        ingredient = new LinkedList<Ingredient>();
        this.name = "";
    }

    /**
     * Get the ID of the Recipe
     *
     * @return the ID
     */
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Recipe (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    @SuppressWarnings ( "unused" )
    private void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Returns name of the recipe.
     *
     * @return Returns the name.
     */
    public String getName () {
        return name;
    }

    /**
     * Sets the recipe name.
     *
     * @param name
     *            The name to set.
     */
    public void setName ( final String name ) {
        this.name = name;
    }

    /**
     * Returns the price of the recipe.
     *
     * @return Returns the price.
     */
    public Integer getPrice () {
        return price;
    }

    /**
     * Sets the recipe price.
     *
     * @param price
     *            The price to set.
     */
    public void setPrice ( final int price ) {
        this.price = price;
    }

    public void addIngredient ( final Ingredient i ) {
        ingredient.add( i );
    }

    public List<Ingredient> getIngredient () {
        return ingredient;
    }

    /**
     * Returns the name of the recipe.
     *
     * @return String
     */
    @Override
    public String toString () {
        return name;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }

    /**
     * Updates Recipe to contain the ingredient set of a different recipe
     *
     * @param r
     *            recipe to model update from
     */
    public void updateRecipe ( final Recipe r ) {
        ingredient = r.getIngredient();
        name = r.getName();
        setId( id );
    }

    /**
     * gets an ingredient from the Recipe by its name
     *
     * @param name
     *            name of the ingredient to get
     * @return the Ingredient object, or null if there is no Ingredient with
     *         that name
     */
    public Ingredient getIngredientByName ( final String name ) {
        for ( int i = 0; i < ingredient.size(); i++ ) {
            if ( ingredient.get( i ).getIngredient().equals( name ) ) {
                return ingredient.get( i );
            }
        }
        return null;
    }

    @Override
    public boolean equals ( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if ( name == null ) {
            if ( other.name != null ) {
                return false;
            }
        }
        else if ( !name.equals( other.name ) ) {
            return false;
        }
        return true;
    }

}
