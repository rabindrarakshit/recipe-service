package com.cuisine.controllers;


import com.cuisine.business.exception.ErrorResponse;
import com.cuisine.business.model.Recipe;
import com.cuisine.business.model.Recipes;
import com.cuisine.business.service.RecipeService;
import com.cuisine.util.RequestValidation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final RequestValidation requestValidation;

    private static final String HTTP_RESPONSE_200_DESCRIPTION = "Success";
    private static final String HTTP_RESPONSE_201_DESCRIPTION = "Created";
    private static final String HTTP_RESPONSE_400_DESCRIPTION = "Bad Request";
    private static final String HTTP_RESPONSE_404_DESCRIPTION = "Not Found";

    public RecipeController(RecipeService recipeService, RequestValidation requestValidation) {
        this.recipeService = recipeService;
        this.requestValidation = requestValidation;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = HTTP_RESPONSE_200_DESCRIPTION, content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Recipes.class)))})
    public ResponseEntity<Recipes> getRecipes(@RequestParam(required = false) Boolean isVeg,
                                              @RequestParam(required = false) Integer numberOfServings,
                                              @RequestParam(required = false) String includeIngredient,
                                              @RequestParam(required = false) String excludeIngredient,
                                              @RequestParam(required = false) String instructionFilter) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(recipeService.getRecipes(isVeg, numberOfServings, includeIngredient, excludeIngredient, instructionFilter));
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = HTTP_RESPONSE_200_DESCRIPTION, content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = HTTP_RESPONSE_400_DESCRIPTION, content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = HTTP_RESPONSE_404_DESCRIPTION, content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)))})
    @RequestMapping(path = "/{recipeId}", method = RequestMethod.PUT)
    public ResponseEntity updateRecipe(@PathVariable Long recipeId, @RequestBody Recipe recipe) {
        requestValidation.validateRecipeUpdate(recipeId, recipe);
        recipeService.updateRecipe(recipeId, recipe);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = HTTP_RESPONSE_201_DESCRIPTION, content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = HTTP_RESPONSE_400_DESCRIPTION, content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity createRecipe(@RequestBody Recipe recipe) {
        requestValidation.validateRecipeInsert(recipe);
        recipeService.createRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = HTTP_RESPONSE_200_DESCRIPTION, content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = HTTP_RESPONSE_400_DESCRIPTION, content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = HTTP_RESPONSE_404_DESCRIPTION, content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)))})
    @RequestMapping(path = "/{recipeId}", method = RequestMethod.DELETE)
    public ResponseEntity removeRecipe(@PathVariable Long recipeId) {
        requestValidation.validateRecipeDelete(recipeId);
        recipeService.removeRecipe(recipeId);
        return ResponseEntity.ok().build();
    }
}
