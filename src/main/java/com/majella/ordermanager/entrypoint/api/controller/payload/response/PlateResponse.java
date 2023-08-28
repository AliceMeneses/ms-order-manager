package com.majella.ordermanager.entrypoint.api.controller.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PlateResponse {

    private String name;
    private BigDecimal price;
    private Integer quantity;

}
