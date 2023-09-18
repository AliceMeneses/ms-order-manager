package com.majella.ordermanager.helper;

import com.majella.ordermanager.entrypoint.api.controller.payload.response.OrderResponse;

import java.math.BigDecimal;
import java.util.List;

import static com.majella.ordermanager.core.domain.Status.IN_PRODUCTION;

public class OrderResponseGenerator {

public static OrderResponse generate(String id) {
        var plateResponse = PlateResponseGenerator.generate();

        return OrderResponse.builder()
                .id(id)
                .status(IN_PRODUCTION)
                .plates(List.of(plateResponse))
                .totalPrice(new BigDecimal(80))
                .build();
    }

}
