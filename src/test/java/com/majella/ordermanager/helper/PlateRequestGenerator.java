package com.majella.ordermanager.helper;

import com.majella.ordermanager.entrypoint.api.controller.payload.request.PlateRequest;

public class PlateRequestGenerator {

    public static PlateRequest generate(String id, Integer quantity) {
        return PlateRequest.builder()
                .id(id)
                .quantity(quantity)
                .build();
    }

}
