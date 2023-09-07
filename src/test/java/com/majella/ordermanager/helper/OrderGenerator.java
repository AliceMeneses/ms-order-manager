package com.majella.ordermanager.helper;

import com.majella.ordermanager.core.domain.Order;
import com.majella.ordermanager.core.domain.Plate;
import com.majella.ordermanager.core.domain.Status;

import java.util.List;

public class OrderGenerator {

    public static Order generate(String orderId, String plateId) {

        var plate = Plate.builder()
                .id(plateId)
                .quantity(2)
                .build();

        return Order.builder()
                .id(orderId)
                .plates(List.of(plate))
                .build();
    }

    public static Order generate(String orderId, List<Plate> plates, Status status) {

        return Order.builder()
                .id(orderId)
                .plates(plates)
                .status(status)
                .build();
    }

}
