package com.majella.ordermanager.dataprovider.database;

import com.majella.ordermanager.dataprovider.database.repository.OrderRepository;
import com.majella.ordermanager.helper.IngredientGenerator;
import com.majella.ordermanager.helper.OrderGenerator;
import com.majella.ordermanager.helper.PlateGenerator;
import com.majella.ordermanager.helper.RecipeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.majella.ordermanager.core.domain.Status.IN_PRODUCTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Order Database Test")
@ExtendWith(MockitoExtension.class)
public class OrderDatabaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderDatabase orderDatabase;

    @Nested
    @DisplayName("Save Test")
    class SaveTest {

        @Test
        @DisplayName("When save order then save")
        public void whenSaveOrderThenReturnOrder() {

            var plateIngredient = IngredientGenerator.generate("Filé de frango",1);
            var plateRecipe = RecipeGenerator.generate("Filé de grelhado", List.of(plateIngredient));
            var plate = PlateGenerator.generator("64f4d44eb35055bb9b2576b8", new BigDecimal(40), plateRecipe, 2);

            var order = OrderGenerator.generate(null, List.of(plate), IN_PRODUCTION);
            var orderWithId = OrderGenerator.generate("64ec17cefc18541d85df2e27", List.of(plate), IN_PRODUCTION);

            when(orderRepository.save(order)).thenReturn(orderWithId);

            var result = orderDatabase.save(order);

            assertThat(result).isEqualTo(orderWithId);
        }

    }

    @Nested
    @DisplayName("Search Test")
    class SearchTest {

        @Test
        @DisplayName("When save order by id then save")
        public void whenSearchOrderByIdThenReturnOrder() {

            var orderId = "64ec17cefc18541d85df2e27";

            var plateIngredient = IngredientGenerator.generate("Filé de frango",1);
            var plateRecipe = RecipeGenerator.generate("Filé de grelhado", List.of(plateIngredient));
            var plate = PlateGenerator.generator("64f4d44eb35055bb9b2576b8", new BigDecimal(40), plateRecipe, 2);

            var order = OrderGenerator.generate(orderId, List.of(plate), IN_PRODUCTION);

            when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

            var result = orderDatabase.search(orderId);

            assertThat(result.isPresent()).isTrue();
            assertThat(result.get()).isEqualTo(order);
        }

    }

}
