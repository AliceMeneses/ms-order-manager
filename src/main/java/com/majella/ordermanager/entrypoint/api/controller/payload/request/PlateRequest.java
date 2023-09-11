package com.majella.ordermanager.entrypoint.api.controller.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlateRequest {

    @NotBlank
    private String id;

    @NotNull
    @Positive
    private Integer quantity;

}
