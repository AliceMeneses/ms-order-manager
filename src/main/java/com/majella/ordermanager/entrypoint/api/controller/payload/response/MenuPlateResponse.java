package com.majella.ordermanager.entrypoint.api.controller.payload.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MenuPlateResponse {

    private String id;
    private String name;
    private BigDecimal price;

}
