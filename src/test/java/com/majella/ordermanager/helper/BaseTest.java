package com.majella.ordermanager.helper;

import com.majella.ordermanager.dataprovider.database.repository.PlateRepository;
import com.majella.ordermanager.dataprovider.database.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.majella.ordermanager.core.domain.Status.IN_PRODUCTION;
import static com.majella.ordermanager.core.domain.Status.READY;

@Component
@Profile("test")
public class BaseTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PlateRepository plateRepository;

    public void clearAll() {
        orderRepository.deleteAll();
        plateRepository.deleteAll();
    }

    public void generateData() {
        var grilledChickenPlate = PlateGenerator.generatorGrilledChickenPlate();
        var beefPlate = PlateGenerator.generatorBeefPlate();

        plateRepository.save(grilledChickenPlate);
        plateRepository.save(beefPlate);

        var order1 = OrderGenerator.generateWithTwoPlates("64fe82fbf968b2939fdd01c7", IN_PRODUCTION);
        var order2 = OrderGenerator.generate("64fe90abf968b2939fdd01e1", READY);

        orderRepository.save(order1);
        orderRepository.save(order2);
    }


}
