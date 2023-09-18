package com.majella.ordermanager.helper;

import com.majella.ordermanager.entrypoint.api.controller.payload.request.OrderRequest;
import com.majella.ordermanager.entrypoint.api.controller.payload.request.PlateRequest;

import java.util.List;

public class OrderRequestGenerator {

    public static OrderRequest generate() {

        var plateRequest = PlateRequestGenerator.generate();

        return OrderRequest.builder()
                .plates(List.of(plateRequest))
                .build();
    }

    public static OrderRequest generateWithPlates(List<PlateRequest> plates) {

        return OrderRequest.builder()
                .plates(plates)
                .build();
    }

    public static OrderRequest generateWithPlatesInNegativeAndZeroQuantity() {

        var plateRequest1 = PlateRequestGenerator.generateWithQuantityEqualToZero();
        var plateRequest2 = PlateRequestGenerator.generateWithNegativeQuantity();
        return OrderRequest.builder()
                .plates(List.of(plateRequest1, plateRequest2))
                .build();
    }

    public static OrderRequest generateWithPlatesThatDoesntExist() {

        var plateThatDoesntExist = PlateRequestGenerator.generateWithIdThatDoesntExist();
        return OrderRequest.builder()
                .plates(List.of(plateThatDoesntExist))
                .build();
    }

}
