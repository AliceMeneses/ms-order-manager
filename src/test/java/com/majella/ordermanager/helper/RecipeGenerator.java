package com.majella.ordermanager.helper;

import com.majella.ordermanager.core.domain.Recipe;

import java.util.List;

public class RecipeGenerator {

    public static Recipe generaterGrilledChickenRecipe() {
        var ingredients = IngredientGenerator.generaterChickenIngredient();
        return Recipe.builder()
                .name("Filé de frango grelhado")
                .ingridients(List.of(ingredients))
                .build();
    }

    public static Recipe generaterBeefRecipe() {
        var ingredients = IngredientGenerator.generaterRumpIngredient();
        return Recipe.builder()
                .name("Bife")
                .ingridients(List.of(ingredients))
                .build();
    }

    public static Recipe randomlyGenerater() {
        var ingredients = IngredientGenerator.randomlyGenerater();

        var FAKER = FakerJavaUtil.getFaker();

        return Recipe.builder()
                .name(FAKER.food().dish())
                .ingridients(List.of(ingredients))
                .build();
    }

}
