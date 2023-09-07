package com.majella.ordermanager.core.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Recipe {

    private String name;
    private List<Ingredient> ingridients;

}
