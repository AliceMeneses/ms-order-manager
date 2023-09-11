package com.majella.ordermanager.helper;

import com.majella.ordermanager.core.domain.Status;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.OrderResponse;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.PlateResponse;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponseGenerator {

public static OrderResponse generate(String id) {
        var plateResponse = PlateResponse.builder()
                .name("Filé de frango à parmegiana")
                .price(new BigDecimal(40))
                .quantity(2)
                .build();

        return OrderResponse.builder()
                .id(id)
                .status(Status.IN_PRODUCTION)
                .plates(List.of(plateResponse))
                .totalPrice(new BigDecimal(80))
                .build();
    }

}
