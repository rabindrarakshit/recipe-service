package com.cuisine.util;


import com.cuisine.data.RecipeRepository;
import com.cuisine.data.entities.RecipeEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// Class used to do the initial checks before we can consider the service as ready

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final RecipeRepository recipeRepository;

    public AppStartupEvent(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<RecipeEntity> recipes = this.recipeRepository.findAll();
        recipes.forEach(System.out::println);
    }
}
