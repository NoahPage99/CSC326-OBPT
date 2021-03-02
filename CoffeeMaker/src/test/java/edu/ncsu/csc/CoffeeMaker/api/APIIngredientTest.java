package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.Assert.assertTrue;
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
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APIIngredientTest {
    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private IngredientService     service;

    /**
     * Sets up the tests.
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    @Test
    @Transactional
    public void ensureIngredient () throws Exception {
        service.deleteAll();

        final Ingredient i = new Ingredient();
        i.setAmount( 5 );
        i.setIngredient( "nutmeg" );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isOk() );

    }

    @Test
    @Transactional
    public void deleteIngredientTest () throws Exception {
        String ingredient = mvc.perform( get( "/api/v1/ingredients" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        /* If there are no recipes, add one */
        if ( !ingredient.isBlank() ) {
            final Ingredient i = new Ingredient();
            i.setIngredient( "nutmeg" );
            i.setAmount( 5 );

            mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isOk() );

            mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isConflict() );

            /* Try get a recipe from the recipe database */
            ingredient = mvc.perform( get( "/api/v1/ingredients" ) ).andDo( print() ).andExpect( status().isOk() )
                    .andReturn().getResponse().getContentAsString();

            assertTrue( ingredient.contains(
                    "nutmeg" ) ); /* Make sure that now our recipe is there */

            mvc.perform( get( "/api/v1/ingredients/idk" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isNotFound() ).andReturn();

            mvc.perform( delete( "/api/v1/ingredients/idk" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isNotFound() ).andReturn();

            mvc.perform( delete( "/api/v1/ingredients/nutmeg" ).contentType( MediaType.APPLICATION_JSON )
                    .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isOk() ).andReturn();

            ingredient = mvc.perform( get( "/api/v1/ingredients" ) ).andDo( print() ).andExpect( status().isOk() )
                    .andReturn().getResponse().getContentAsString();

            /* If there is only one recipe, then it should be empty */
            assertTrue( !ingredient.contains( "nutmeg" ) );

        }

    }
}
