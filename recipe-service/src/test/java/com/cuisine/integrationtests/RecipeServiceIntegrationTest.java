package com.cuisine.integrationtests;

import com.cuisine.RecipesApplication;
import com.cuisine.business.model.Recipe;
import com.cuisine.business.model.Recipes;
import com.cuisine.data.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static com.cuisine.util.TestUtil.getObject;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = RecipesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeServiceIntegrationTest {
    @Autowired
    private RecipeRepository recipeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddRecipe() {
        Recipe recipe = getObject("src/test/resources/recipe.json", Recipe.class);
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/recipes", recipe, String.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
        Recipe result = this.restTemplate
                .getForObject("http://localhost:" + port + "/recipes", Recipes.class).getRecipes().get(0);
        this.restTemplate.delete("http://localhost:" + port + "/recipes/" + result.getRecipeId());
    }

    @Test
    public void testGetRecipe() {
        Recipe recipe = getObject("src/test/resources/recipe.json", Recipe.class);
        this.restTemplate.postForEntity("http://localhost:" + port + "/recipes", recipe, String.class);
        Recipe result = this.restTemplate.getForObject("http://localhost:" + port + "/recipes", Recipes.class).getRecipes().get(0);
        assertTrue(result.getName().equalsIgnoreCase("pizza"));
        this.restTemplate.delete("http://localhost:" + port + "/recipes/" + result.getRecipeId());
    }

    @Test
    public void testUpdateRecipe() {
        Recipe recipe = getObject("src/test/resources/recipe.json", Recipe.class);
        this.restTemplate.postForEntity("http://localhost:" + port + "/recipes", recipe, String.class);
        Recipe result = this.restTemplate.getForObject("http://localhost:" + port + "/recipes", Recipes.class).getRecipes().get(0);
        assertTrue(result.getName().equalsIgnoreCase("pizza"));
        recipe.setName("pineapple pizza");
        this.restTemplate.put("http://localhost:" + port + "/recipes/" + result.getRecipeId(), recipe);
        assertTrue(
                this.restTemplate
                        .getForObject("http://localhost:" + port + "/recipes", Recipes.class)
                        .getRecipes().get(0).getName().equalsIgnoreCase("pineapple pizza"));
        this.restTemplate.delete("http://localhost:" + port + "/recipes/" + result.getRecipeId());
    }

    @Test
    public void testRemoveRecipe() {
        Recipe recipe = getObject("src/test/resources/recipe.json", Recipe.class);
        this.restTemplate
                .postForEntity("http://localhost:" + port + "/recipes", recipe, String.class);
        Recipe result = this.restTemplate
                .getForObject("http://localhost:" + port + "/recipes", Recipes.class)
                .getRecipes().get(0);
        assertTrue(result.getName().equalsIgnoreCase("pizza"));
        this.restTemplate.delete("http://localhost:" + port + "/recipes/" + result.getRecipeId());
        Assertions.assertEquals(0, this.restTemplate
                .getForObject("http://localhost:" + port + "/recipes", Recipes.class)
                .getRecipes().size());
    }
}