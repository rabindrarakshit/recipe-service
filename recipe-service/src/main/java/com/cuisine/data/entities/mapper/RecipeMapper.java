package com.cuisine.data.entities.mapper;

import com.cuisine.business.model.Recipe;
import com.cuisine.data.entities.RecipeEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {
    public static Recipe convertEntityToBusinessModel(RecipeEntity recipeEntity) {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeEntity.getRecipeId());
        recipe.setName(recipeEntity.getName());
        recipe.setIsVeg(recipeEntity.getIsVeg());
        recipe.setNumberOfServings(recipeEntity.getNumberOfServings());
        if (StringUtils.isNotEmpty(recipeEntity.getInstructions())) {
            recipe.setInstructions(convertCSVToList(recipeEntity.getInstructions()));
        }
        if (StringUtils.isNotEmpty(recipeEntity.getInstructions())) {
            recipe.setIngredients(convertCSVToList(recipeEntity.getIngredients()));
        }
        return recipe;
    }

    public static RecipeEntity convertBusinessToEntityModel(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setName(StringUtils.lowerCase(recipe.getName()));
        recipeEntity.setIsVeg(recipe.getIsVeg());
        recipeEntity.setNumberOfServings(recipe.getNumberOfServings());
        if (!CollectionUtils.isEmpty(recipe.getIngredients())) {
            recipeEntity.setIngredients(convertListToCSV(convertListToLowerCase(recipe.getIngredients())));
        }
        if (!CollectionUtils.isEmpty(recipe.getInstructions())) {
            recipeEntity.setInstructions(convertListToCSV(convertListToLowerCase(recipe.getInstructions())));
        }
        return recipeEntity;
    }

    public static List<String> convertCSVToList(String csv) {
        return Arrays.asList(csv.split("\\s*,\\s*"));
    }

    public static String convertListToCSV(List<String> listOfString) {
        return String.join(",", listOfString);
    }

    public static List<String> convertListToLowerCase(List<String> list) {
        return list.stream()
                .map(StringUtils::lowerCase)
                .collect(Collectors.toList());
    }
}
