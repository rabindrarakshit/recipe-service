package com.cuisine.util;

import com.cuisine.business.exception.BusinessException;
import com.cuisine.business.model.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RequestValidation {
    public void validateRecipeInsert(Recipe recipe) {
        if (StringUtils.isEmpty(recipe.getName())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), "Please provide name of the recipe");
        }
        if (recipe.getNumberOfServings() == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), "Please provide number of servings");
        }
        if (recipe.getNumberOfServings() <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), "Number of servings should always be " +
                    "positive");
        }
        if (recipe.getIsVeg() == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), "Please mention the recipe is veg or " +
                    "non-veg");
        }
    }

    public void validateRecipeUpdate(Long recipeId, Recipe recipe) {
        if (recipeId == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), "RecipeId is either null/empty");
        }
        if (recipe.getNumberOfServings() != null && recipe.getNumberOfServings() <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), "Number of servings should always be " +
                    "positive");
        }
    }

    public void validateRecipeDelete(Long recipeId) {
        if (recipeId == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value(), "RecipeId is either null/empty");
        }
    }
}
