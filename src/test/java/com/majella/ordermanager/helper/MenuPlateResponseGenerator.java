package com.majella.ordermanager.helper;

import com.majella.ordermanager.entrypoint.api.controller.payload.response.MenuPlateResponse;

import java.math.BigDecimal;

public class MenuPlateResponseGenerator {

    public static MenuPlateResponse generateWithGrilledChicken() {
        return MenuPlateResponse.builder()
                .id("64f4d44eb35055bb9b2576b8")
                .name("Filé de frango grelhado")
                .price(new BigDecimal(40))
                .build();
    }

    public static MenuPlateResponse generateWithBeef() {
        return MenuPlateResponse.builder()
                .id("64f4d464b35055bb9b2576b9")
                .name("Bife")
                .price(new BigDecimal(60))
                .build();
    }

}
