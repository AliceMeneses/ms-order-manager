package com.majella.ordermanager.helper;

import com.majella.ordermanager.dataprovider.database.repository.OrderRepository;
import com.majella.ordermanager.dataprovider.database.repository.PlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

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
        var plate1Ingredient = IngredientGenerator.generate("Filé de frango",1);
        var plate1Recipe = RecipeGenerator.generate("Filé de frango à parmegiana", List.of(plate1Ingredient));
        var plate1 = PlateGenerator.generator("64fe7441f968b2939fdd01c6", new BigDecimal(40), plate1Recipe, 2);

        var plate2Ingredient = IngredientGenerator.generate("Bife de alcatra",1);
        var plate2Recipe = RecipeGenerator.generate("Bife", List.of(plate2Ingredient));
        var plate2 = PlateGenerator.generator("64f4d464b35055bb9b2576b9", new BigDecimal(60), plate2Recipe, 4);

        plateRepository.save(plate1);
        plateRepository.save(plate2);

        var order1 = OrderGenerator.generate("64fe82fbf968b2939fdd01c7", List.of(plate1, plate2), IN_PRODUCTION);
        var order2 = OrderGenerator.generate("64fe90abf968b2939fdd01e1", List.of(plate1), READY);

        orderRepository.save(order1);
        orderRepository.save(order2);
    }


}
