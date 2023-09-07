package com.majella.ordermanager.entrypoint.api.controller.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PlateResponse {

    private String name;
    private BigDecimal price;
    private Integer quantity;

}
