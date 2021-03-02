package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class APIIngredientController extends APIController {

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private InventoryService  inventoryService;

    /**
     * REST API method to provide GET access to all ingredients in the system
     *
     * @return JSON representation of all ingredients
     */
    @GetMapping ( BASE_PATH + "/ingredients" )
    public List<Ingredient> getIngredients () {
        return (List<Ingredient>) ingredientService.findAll();
    }

    @GetMapping ( BASE_PATH + "/ingredients/{name}" )
    public ResponseEntity getIngredient ( @PathVariable ( "name" ) final String name ) {

        final Ingredient ingr = ingredientService.findByName( name );
        return null == ingr
                ? new ResponseEntity( errorResponse( "No recipe found with name " + name ), HttpStatus.NOT_FOUND )
                : new ResponseEntity( ingr, HttpStatus.OK );
    }

    @PostMapping ( BASE_PATH + "/ingredients" )
    public ResponseEntity createIngredient ( @RequestBody final Ingredient ingredient ) {
        if ( null != ingredientService.findByName( ingredient.getIngredient() ) ) {
            return new ResponseEntity(
                    successResponse( "Ingredient with the name " + ingredient.getIngredient() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
        final Inventory currentInventory = inventoryService.getInventory();
        currentInventory.addIngredient( ingredient );
        ingredientService.save( ingredient );
        inventoryService.save( currentInventory );
        return new ResponseEntity( successResponse( ingredient.getIngredient() + " successfully created" ),
                HttpStatus.OK );

    }

    @DeleteMapping ( BASE_PATH + "/ingredients/{name}" )
    public ResponseEntity deleteRecipe ( @PathVariable final String name ) {
        final Ingredient ingredient = ingredientService.findByName( name );
        if ( null == ingredient ) {
            return new ResponseEntity( errorResponse( "No ingredient found for name " + name ), HttpStatus.NOT_FOUND );
        }
        final Inventory currentInventory = inventoryService.getInventory();
        currentInventory.deleteIngredient( ingredient.getIngredient() );
        ingredientService.delete( ingredient );
        inventoryService.save( currentInventory );

        return new ResponseEntity( successResponse( name + " was deleted successfully" ), HttpStatus.OK );
    }

}
