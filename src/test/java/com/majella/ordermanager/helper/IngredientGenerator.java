package com.majella.ordermanager.helper;

import com.majella.ordermanager.core.domain.Ingredient;

public class IngredientGenerator {

    public static Ingredient generate(String name, Integer quantity) {
        return Ingredient.builder()
                .name(name)
                .quantity(quantity)
                .build();
    }

}
