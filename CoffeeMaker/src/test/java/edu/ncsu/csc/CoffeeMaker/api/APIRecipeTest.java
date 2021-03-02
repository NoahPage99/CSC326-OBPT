package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APIRecipeTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RecipeService         service;

    /**
     * Sets up the tests.
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    @Test
    @Transactional
    public void ensureRecipe () throws Exception {

        String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        /* Figure out if the recipe we want is present */
        if ( !recipe.contains( "Mocha" ) ) {
            final Recipe r = new Recipe();
            r.addIngredient( new Ingredient( "chocolate", 5 ) );
            r.addIngredient( new Ingredient( "coffee", 3 ) );
            r.addIngredient( new Ingredient( "milk", 4 ) );
            r.addIngredient( new Ingredient( "sugar", 8 ) );
            r.setPrice( 10 );
            r.setName( "Mocha" );

            mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

        }

        final Recipe r2 = new Recipe();
        r2.addIngredient( new Ingredient( "chocolate", 5 ) );
        r2.setPrice( 10 );
        r2.setName( "coffee" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r2 ) ) ).andExpect( status().isOk() );

        final Recipe r3 = new Recipe();
        r3.addIngredient( new Ingredient( "chocolate", 5 ) );
        r3.setPrice( 10 );
        r3.setName( "coffee" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r3 ) ) ).andExpect( status().isConflict() );

        final Recipe r4 = new Recipe();
        r4.addIngredient( new Ingredient( "yes", 5 ) );
        r4.setPrice( 10 );
        r4.setName( "yes" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r4 ) ) ).andExpect( status().isOk() );

        final Recipe r5 = new Recipe();
        r5.addIngredient( new Ingredient( "yes2", 5 ) );
        r5.setPrice( 10 );
        r5.setName( "yes2" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r5 ) ) ).andExpect( status().isInsufficientStorage() );

        mvc.perform( delete( "/api/v1/recipes/Mocha" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r5 ) ) ).andExpect( status().isOk() ).andReturn();

        mvc.perform( delete( "/api/v1/recipes/this" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r2 ) ) ).andExpect( status().isNotFound() ).andReturn();

        recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();

        // service.deleteAll();
        //
        // final Recipe r = new Recipe();
        // r.addIngredient( new Ingredient( "chocolate", 5 ) );
        // r.addIngredient( new Ingredient( "coffee", 3 ) );
        // r.addIngredient( new Ingredient( "milk", 4 ) );
        // r.addIngredient( new Ingredient( "sugar", 8 ) );
        // r.setPrice( 10 );
        // r.setName( "Mocha" );
        //
        // mvc.perform( post( "/api/v1/recipes" ).contentType(
        // MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk()
        // );

    }

}
