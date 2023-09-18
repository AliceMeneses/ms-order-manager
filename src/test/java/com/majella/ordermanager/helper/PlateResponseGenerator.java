package com.majella.ordermanager.helper;

import com.majella.ordermanager.entrypoint.api.controller.payload.response.PlateResponse;

import java.math.BigDecimal;

public class PlateResponseGenerator {

    public static PlateResponse generate() {
        return PlateResponse.builder()
                .name("Filé de frango grelhado")
                .price(new BigDecimal(40))
                .quantity(2)
                .build();
    }

}
