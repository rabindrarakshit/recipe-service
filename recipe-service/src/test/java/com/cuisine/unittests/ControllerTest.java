package com.cuisine.unittests;

import com.cuisine.business.exception.BusinessException;
import com.cuisine.business.model.Recipe;
import com.cuisine.business.model.Recipes;
import com.cuisine.business.service.RecipeService;
import com.cuisine.controllers.RecipeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.cuisine.util.TestUtil.getObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
    @MockBean
    private RecipeService recipeService;
    private Recipes recipes;
    private Recipe recipe;
    private Recipe invalidRecipe;
    @Autowired
    private RecipeController recipeController;

    public ControllerTest() {
        recipe = getObject("src/test/resources/recipe.json", Recipe.class);
        recipes = getObject("src/test/resources/recipes.json", Recipes.class);
        invalidRecipe = getObject("src/test/resources/invalid_recipe.json", Recipe.class);
    }

    @Test
    public void testGetRecipesWithoutAnyFilter() {
        when(recipeService.getRecipes(null, null, null, null, null))
                .thenReturn(recipes);
        ResponseEntity<Recipes> recipesResponseEntity = recipeController.getRecipes(null, null, null, null, null);
        assertThat(recipesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(recipesResponseEntity.getBody()).isEqualTo(recipes);
    }

    @Test
    public void testDeleteRecipe() {
        Mockito.doNothing().when(recipeService).removeRecipe(2L);
        ResponseEntity responseEntity = recipeController.removeRecipe(2L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCreateRecipe() {
        Mockito.doNothing().when(recipeService).createRecipe(recipe);
        ResponseEntity responseEntity = recipeController.createRecipe(recipe);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCreateInvalidRecipe() {
        assertThrows(BusinessException.class, () ->  recipeController.createRecipe(invalidRecipe));
    }

    @Test
    public void testUpdateRecipe() {
        Mockito.doNothing().when(recipeService).updateRecipe(1L, recipe);
        ResponseEntity responseEntity = recipeController.updateRecipe(recipe.getRecipeId(), recipe);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testUpdateInvalidRecipe() {
        assertThrows(BusinessException.class, () ->  recipeController.updateRecipe(1L, invalidRecipe));
    }
}
