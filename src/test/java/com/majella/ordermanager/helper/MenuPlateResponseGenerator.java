package com.majella.ordermanager.helper;

import com.majella.ordermanager.entrypoint.api.controller.payload.response.MenuPlateResponse;

import java.math.BigDecimal;

public class MenuPlateResponseGenerator {

    public static MenuPlateResponse generate(String id, String name, BigDecimal price) {
        return MenuPlateResponse.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }

}
