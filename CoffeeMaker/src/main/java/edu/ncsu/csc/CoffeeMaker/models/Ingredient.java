package edu.ncsu.csc.CoffeeMaker.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import edu.ncsu.csc.CoffeeMaker.models.enums.IngredientType;

@Entity
public class Ingredient extends DomainObject {

    /** Recipe id */
    @Id
    @GeneratedValue
    private Long           id;

    @Enumerated ( EnumType.STRING )
    private IngredientType ingredient;

    private int            amount;

    public Ingredient ( final IngredientType ingredient, final int amount ) {
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

    public IngredientType getIngredient () {
        return ingredient;
    }

    public void setIngredient ( final IngredientType ingredient ) {
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
