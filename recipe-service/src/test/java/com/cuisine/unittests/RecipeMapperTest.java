package com.cuisine.unittests;

import com.cuisine.business.model.Recipes;
import com.cuisine.data.entities.RecipeEntity;
import com.cuisine.data.entities.mapper.RecipeMapper;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.cuisine.util.TestUtil.getObject;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipeMapperTest {
    private Recipes recipes;
    private List<RecipeEntity> recipeEntityList;
    public RecipeMapperTest(){
        recipes = getObject("src/test/resources/recipes.json", Recipes.class);
    }

    @Test
    public void testCorrectMappingFromBusinessToEntity() {
        recipeEntityList = recipes.getRecipes()
                .stream()
                .map(RecipeMapper::convertBusinessToEntityModel)
                .collect(Collectors.toList());

        assertThat(recipeEntityList.size()).isEqualTo(3);
        assertThat(recipeEntityList.get(0).getIngredients()).isEqualTo("flour,paprika");
    }
}
