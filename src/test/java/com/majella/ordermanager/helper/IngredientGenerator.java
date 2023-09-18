package com.majella.ordermanager.helper;

import com.majella.ordermanager.core.domain.Ingredient;

public class IngredientGenerator {

    public static Ingredient generaterChickenIngredient() {
        return Ingredient.builder()
                .name("Filé de frango")
                .quantity(1)
                .build();
    }

    public static Ingredient generaterRumpIngredient() {
        return Ingredient.builder()
                .name("Alcatra")
                .quantity(1)
                .build();
    }

    public static Ingredient randomlyGenerater() {
        var FAKER = FakerJavaUtil.getFaker();

        return Ingredient.builder()
                .name(FAKER.food().ingredient())
                .quantity(FAKER.number().randomDigit())
                .build();
    }

}
