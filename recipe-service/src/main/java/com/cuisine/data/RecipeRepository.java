package com.cuisine.data;


import com.cuisine.data.entities.RecipeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<RecipeEntity, Long> {
}
