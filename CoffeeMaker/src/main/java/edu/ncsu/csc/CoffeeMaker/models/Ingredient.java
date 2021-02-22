package edu.ncsu.csc.CoffeeMaker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ingredient extends DomainObject {

    /** Recipe id */
    @Id
    @GeneratedValue
    private Long   id;

<<<<<<< HEAD
=======
    // @Enumerated ( EnumType.STRING )
    // private IngredientType ingredient;
>>>>>>> 912796cbc7c14d9ff1cd96db841904ea50392081
    private String ingredient;

    private int    amount;

    public Ingredient ( final String ingredient, final int amount ) {
        super();
        // this.id = id;
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
        this.amount = amount;
    }

    @Override
    public String toString () {
        return "Ingredient [id=" + id + ", ingredient=" + ingredient + ", amount=" + amount + "]";
    }

}