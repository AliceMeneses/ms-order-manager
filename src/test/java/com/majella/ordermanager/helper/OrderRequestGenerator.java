package com.majella.ordermanager.helper;

import com.majella.ordermanager.entrypoint.api.controller.payload.request.OrderRequest;
import com.majella.ordermanager.entrypoint.api.controller.payload.request.PlateRequest;

import java.util.List;

public class OrderRequestGenerator {

    public static OrderRequest generate(String plateId) {

        var plateRequest = PlateRequestGenerator.generate(plateId, 2);

        return OrderRequest.builder()
                .plates(List.of(plateRequest))
                .build();
    }

    public static OrderRequest generateWithPlates(List<PlateRequest> plates) {

        return OrderRequest.builder()
                .plates(plates)
                .build();
    }

}
