package com.majella.ordermanager.helper;

import com.majella.ordermanager.entrypoint.api.controller.payload.request.OrderRequest;
import com.majella.ordermanager.entrypoint.api.controller.payload.request.PlateRequest;

import java.util.List;

public class OrderRequestGenerator {

    public static OrderRequest generate(String plateId) {

        var plateRequest = PlateRequest.builder()
                .id(plateId)
                .quantity(2)
                .build();

        return OrderRequest.builder()
                .plates(List.of(plateRequest))
                .build();
    }

}
