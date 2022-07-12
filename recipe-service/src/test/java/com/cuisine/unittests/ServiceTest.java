package com.cuisine.unittests;

import com.cuisine.business.model.Recipe;
import com.cuisine.business.model.Recipes;
import com.cuisine.business.service.RecipeService;
import com.cuisine.data.RecipeRepository;
import com.cuisine.data.entities.RecipeEntity;
import com.cuisine.data.entities.mapper.RecipeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static com.cuisine.util.TestUtil.getObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    @Autowired
    private RecipeService recipeService;

    private Recipes recipes;
    private List<RecipeEntity> recipeEntityList;
    @MockBean
    private RecipeRepository recipeRepository;

    public ServiceTest() {
        recipes = getObject("src/test/resources/recipes.json", Recipes.class);
        recipeEntityList = recipes.getRecipes()
                .stream()
                .map(RecipeMapper::convertBusinessToEntityModel)
                .collect(Collectors.toList());
    }

    @Test
    public void testGetRecipesWithoutAnyFilter() {
        when(recipeRepository.findAll()).thenReturn(recipeEntityList);
        assertThat(recipeService
                .getRecipes(null, null, null, null, null)
                .getRecipes()
                .size())
                .isEqualTo(3);
    }

    @Test
    public void testGetVegRecipes() {
        when(recipeRepository.findAll()).thenReturn(recipeEntityList);
        List<Recipe> result = recipeService
                .getRecipes(true, null, null, null, null)
                .getRecipes();
        assertThat(result.get(0).getName()).isEqualTo("pasta");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testGetNonVegRecipes() {
        when(recipeRepository.findAll()).thenReturn(recipeEntityList);
        List<Recipe> result = recipeService
                .getRecipes(false, null, null, null, null)
                .getRecipes();
        assertThat(result.get(0).getName()).isEqualTo("pizza");
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void testGetNonVegRecipesWhichServesThreePeople() {
        when(recipeRepository.findAll()).thenReturn(recipeEntityList);
        List<Recipe> result = recipeService
                .getRecipes(false, 3, null, null, null)
                .getRecipes();
        assertThat(result.get(0).getName()).isEqualTo("butter chicken");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testGetNonVegRecipesWhichHasFlourAsAnIngredient() {
        when(recipeRepository.findAll()).thenReturn(recipeEntityList);
        List<Recipe> result = recipeService
                .getRecipes(false, null, "flour", null, null)
                .getRecipes();
        assertThat(result.get(0).getName()).isEqualTo("pizza");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testGetNonVegRecipesWhichDoesNotHaveFlourAsAnIngredient() {
        when(recipeRepository.findAll()).thenReturn(recipeEntityList);
        List<Recipe> result = recipeService
                .getRecipes(false, null, null, "flour", null)
                .getRecipes();
        assertThat(result.get(0).getName()).isEqualTo("butter chicken");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testGetRecipesWhichHasOvenInInstructions() {
        when(recipeRepository.findAll()).thenReturn(recipeEntityList);
        List<Recipe> result = recipeService
                .getRecipes(null, null, null, null, "oven")
                .getRecipes();
        assertThat(result.get(0).getName()).isEqualTo("pizza");
        assertThat(result.size()).isEqualTo(2);
    }
}
