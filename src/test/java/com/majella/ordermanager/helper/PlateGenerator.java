package com.majella.ordermanager.helper;

import com.majella.ordermanager.core.domain.Plate;
import com.majella.ordermanager.core.domain.Recipe;

import java.math.BigDecimal;

public class PlateGenerator {

    public static Plate generator(String id, BigDecimal price, Recipe recipe, Integer quantity) {
        return Plate.builder()
            .id(id)
            .price(price)
            .recipe(recipe)
            .quantity(quantity)
            .build();
    }

}
