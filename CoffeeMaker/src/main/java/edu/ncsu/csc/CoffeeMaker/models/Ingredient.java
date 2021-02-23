package edu.ncsu.csc.CoffeeMaker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class Ingredient extends DomainObject {

    /** Recipe id */
    @Id
    @GeneratedValue
    private Long   id;

    private String ingredient;

    @Min ( 0 )
    private int    amount;

    public Ingredient ( final String ingredient, final int amount ) {
        super();
        // this.id = id;
        if ( amount < 0 ) {
            throw new IllegalArgumentException( "Amount cannot be negative" );
        }
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Ingredient () {

    }

    public Long getId () {
        return id;
    }

    public void setId ( final Long id ) {
        this.id = id;
    }

    public String getIngredient () {
        return ingredient;
    }

    public void setIngredient ( final String ingredient ) {
        this.ingredient = ingredient;
    }

    public int getAmount () {
        return amount;
    }

    public void setAmount ( final int amount ) {
        if ( amount < 0 ) {
            throw new IllegalArgumentException( "Amount cannot be negative" );
        }
        this.amount = amount;
    }

    @Override
    public String toString () {
        return "Ingredient [ingredient=" + ingredient + ", amount=" + amount + "]";
    }

}
