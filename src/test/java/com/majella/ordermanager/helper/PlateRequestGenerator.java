package com.majella.ordermanager.helper;

import com.majella.ordermanager.entrypoint.api.controller.payload.request.PlateRequest;

public class PlateRequestGenerator {

    public static PlateRequest generate() {
        return PlateRequest.builder()
                .id("64f4d44eb35055bb9b2576b8")
                .quantity(2)
                .build();
    }

    public static PlateRequest generateWithQuantityEqualToZero() {
        return PlateRequest.builder()
                .id("64fe7441f968b2939fdd01c6")
                .quantity(0)
                .build();
    }

    public static PlateRequest generateWithNegativeQuantity() {
        return PlateRequest.builder()
                .id("64f4c8cab35055bb9b2576b7")
                .quantity(-8)
                .build();
    }

    public static PlateRequest generateWithIdThatDoesntExist() {
        return PlateRequest.builder()
                .id("64f4d44eb25055bb9b257689")
                .quantity(2)
                .build();
    }

}
