package com.majella.ordermanager.entrypoint.api.controller.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuPlateResponse {

    private String id;
    private String name;
    private BigDecimal price;

}
