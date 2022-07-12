package com.cuisine.business.service;

import com.cuisine.business.exception.BusinessException;
import com.cuisine.business.model.Recipe;
import com.cuisine.business.model.Recipes;
import com.cuisine.data.RecipeRepository;
import com.cuisine.data.entities.RecipeEntity;
import com.cuisine.data.entities.mapper.RecipeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cuisine.data.entities.mapper.RecipeMapper.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipes getRecipes(Boolean vegOnly, Integer numberOfServings, String includeIngredient, String excludeIngredient, String instructionFilter) {
        List<RecipeEntity> recipeEntities = new ArrayList<>();
        recipeRepository.findAll().forEach(recipeEntities::add);
        List<Recipe> recipes = recipeEntities.stream()
                .map(RecipeMapper::convertEntityToBusinessModel)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(recipes)) {
            if (vegOnly != null) {
                recipes = recipes.stream()
                        .filter(recipe -> recipe.getIsVeg().equals(vegOnly))
                        .collect(Collectors.toList());
            }
            if (numberOfServings != null) {
                recipes = recipes.stream()
                        .filter(recipe -> recipe.getNumberOfServings().equals(numberOfServings))
                        .collect(Collectors.toList());
            }
            if (StringUtils.isNotEmpty(includeIngredient)) {
                recipes = recipes.stream()
                        .filter(recipe -> recipe.getIngredients() != null)
                        .filter(recipe -> recipe.getIngredients().contains(StringUtils.lowerCase(includeIngredient)))
                        .collect(Collectors.toList());
            }
            if (StringUtils.isNotEmpty(excludeIngredient)) {
                recipes = recipes.stream()
                        .filter(recipe -> recipe.getIngredients() != null)
                        .filter(recipe -> !recipe.getIngredients().contains(StringUtils.lowerCase(excludeIngredient)))
                        .collect(Collectors.toList());
            }
            if (StringUtils.isNotEmpty(instructionFilter)) {
                recipes = recipes.stream()
                        .filter(recipe -> recipe.getInstructions() != null)
                        .filter(recipe -> recipe.getInstructions().contains(StringUtils.lowerCase(instructionFilter)))
                        .collect(Collectors.toList());
            }
        }
        return Recipes.builder()
                .setRecipes(recipes)
                .build();
    }

    public void createRecipe(Recipe recipe) {
        recipeRepository.save(convertBusinessToEntityModel(recipe));
    }

    public void updateRecipe(Long recipeId, Recipe recipe) {
        Optional<RecipeEntity> recipeEntityOptional = recipeRepository.findById(recipeId);
        if (recipeEntityOptional.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value(), "No recipes found for the given Id");
        }
        RecipeEntity recipeEntity = recipeEntityOptional.get();
        if (StringUtils.isNotEmpty(recipe.getName())) {
            recipeEntity.setName(StringUtils.lowerCase(recipe.getName()));
        }
        if (recipe.getIsVeg() != null) {
            recipeEntity.setIsVeg(recipe.getIsVeg());
        }
        if (!CollectionUtils.isEmpty(recipe.getIngredients())) {
            recipeEntity.setIngredients(convertListToCSV(convertListToLowerCase(recipe.getIngredients())));
        }
        if (!CollectionUtils.isEmpty(recipe.getInstructions())) {
            recipeEntity.setInstructions(convertListToCSV(convertListToLowerCase(recipe.getInstructions())));
        }
        if (recipe.getNumberOfServings() != null) {
            recipeEntity.setNumberOfServings(recipe.getNumberOfServings());
        }
        recipeRepository.save(recipeEntity);
    }

    public void removeRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }
}
