package com.majella.ordermanager.helper;

import com.majella.ordermanager.core.domain.Plate;

import java.math.BigDecimal;

public class PlateGenerator {

    public static Plate generatorGrilledChickenPlate() {
        var recipe = RecipeGenerator.generaterGrilledChickenRecipe();

        return Plate.builder()
            .id("64f4d44eb35055bb9b2576b8")
            .price(new BigDecimal(40))
            .recipe(recipe)
            .quantity(2)
            .build();
    }

    public static Plate generatorReferenceToTheGrilledChickenPlate() {
        return Plate.builder()
                .id("64f4d44eb35055bb9b2576b8")
                .quantity(2)
                .build();
    }

    public static Plate generatorBeefPlate() {
        var recipe = RecipeGenerator.generaterBeefRecipe();

        return Plate.builder()
                .id("64f4d464b35055bb9b2576b9")
                .price(new BigDecimal(60))
                .recipe(recipe)
                .quantity(2)
                .build();
    }

    public static Plate generatorReferenceToTheBeefPlate() {
        return Plate.builder()
                .id("64f4d464b35055bb9b2576b9")
                .quantity(2)
                .build();
    }

    public static Plate randomlyGenerator() {
        var recipe = RecipeGenerator.randomlyGenerater();

        var FAKER = FakerJavaUtil.getFaker();

        return Plate.builder()
                .id(FAKER.number().digit())
                .price(new BigDecimal(FAKER.number().randomNumber(2,true)))
                .recipe(recipe)
                .quantity(FAKER.number().randomDigit())
                .build();
    }

}
