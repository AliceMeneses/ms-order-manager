package com.majella.ordermanager.entrypoint.api.controller.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class PlateRequest {

    @NotBlank
    private String id;

    @NotNull
    @Positive
    private Long quantity;

}
