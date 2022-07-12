package com.cuisine.business.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonDeserialize(builder = Recipes.Builder.class)
@AutoValue
public abstract class Recipes {
    public abstract List<Recipe> getRecipes();

    public abstract Builder toBuilder();

    @NotNull
    public static Builder builder() {
        return Builder.builder();
    }

    @JsonPOJOBuilder(withPrefix = "set")
    @JsonIgnoreProperties(ignoreUnknown = true)
    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setRecipes(List<Recipe> recipes);

        @JsonCreator
        public static Builder builder() {
            return new AutoValue_Recipes.Builder();
        }

        public abstract Recipes build();
    }
}
