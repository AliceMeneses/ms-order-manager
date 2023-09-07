package com.majella.ordermanager.helper;

import com.majella.ordermanager.core.domain.Ingredient;
import com.majella.ordermanager.core.domain.Recipe;

import java.util.List;

public class RecipeGenerator {

    public static Recipe generate(String name, List<Ingredient> ingredients) {
        return Recipe.builder()
                .name(name)
                .ingridients(ingredients)
                .build();
    }

}
