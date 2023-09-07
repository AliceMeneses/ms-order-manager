package com.majella.ordermanager.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Ingredient {

    private String name;
    private Integer quantity;

}
