package com.majella.ordermanager.helper;

import com.majella.ordermanager.core.domain.Order;
import com.majella.ordermanager.core.domain.Plate;
import com.majella.ordermanager.core.domain.Status;

import java.util.ArrayList;
import java.util.List;

import static com.majella.ordermanager.core.domain.Status.IN_PRODUCTION;

public class OrderGenerator {

    public static Order generate(String id, Status status) {

        var grilledChickenPlate = PlateGenerator.generatorGrilledChickenPlate();
        return Order.builder()
                .id(id)
                .plates(List.of(grilledChickenPlate))
                .status(status)
                .build();
    }

    public static Order generateReference() {

        var grilledChickenPlate = PlateGenerator.generatorReferenceToTheGrilledChickenPlate();
        return Order.builder()
                .plates(List.of(grilledChickenPlate))
                .status(IN_PRODUCTION)
                .build();
    }

    public static Order generateWithTwoPlates(String id, Status status) {

        var grilledChickenPlate = PlateGenerator.generatorGrilledChickenPlate();
        var beefPlate = PlateGenerator.generatorBeefPlate();

        return Order.builder()
                .id(id)
                .plates(List.of(grilledChickenPlate, beefPlate))
                .status(status)
                .build();
    }

    public static Order generateWithReferenceToTheTwoPlates() {

        List<Plate> plates = new ArrayList<>();

        var grilledChickenPlate = PlateGenerator.generatorReferenceToTheGrilledChickenPlate();
        var beefPlate = PlateGenerator.generatorReferenceToTheBeefPlate();

        plates.add(grilledChickenPlate);
        plates.add(beefPlate);

        return Order.builder()
                .plates(plates)
                .status(IN_PRODUCTION)
                .build();
    }

}
