package com.majella.ordermanager.core.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Recipe {

    private String name;
    private List<Ingredient> ingridients;

}
