package edu.ncsu.csc.CoffeeMaker.services;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.repositories.IngredientRepository;

@Component
@Transactional
public class IngredientService extends Service {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    protected JpaRepository getRepository () {
        return ingredientRepository;
    }

    public Ingredient findByName ( final String ingredient ) {
        final Ingredient i = new Ingredient();
        i.setIngredient( ingredient );

        final ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths( "amount" ).withMatcher( "ingredient",
                ExampleMatcher.GenericPropertyMatchers.exact() );

        final Example<Ingredient> example = Example.of( i, matcher );

        try {
            return ingredientRepository.findOne( example ).get();
        }
        catch ( final NoSuchElementException nsee ) {
            return null;
        }
    }

}
