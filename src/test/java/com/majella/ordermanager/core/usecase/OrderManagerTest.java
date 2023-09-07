package com.majella.ordermanager.core.usecase;

import com.majella.ordermanager.core.exception.OrderCantBeCanceledException;
import com.majella.ordermanager.core.exception.OrderNotFoundException;
import com.majella.ordermanager.core.exception.PlateNotFoundException;
import com.majella.ordermanager.core.gateway.OrderGateway;
import com.majella.ordermanager.core.gateway.PlateGateway;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.majella.ordermanager.core.domain.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("Order Manager Test")
@ExtendWith(MockitoExtension.class)
public class OrderManagerTest {

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private PlateGateway plateGateway;

    @InjectMocks
    private OrderManager orderManager;

    @DisplayName("Create Test")
    @Nested
    class CreateTest {

        @Test
        @DisplayName("When create order then return order")
        public void whenCreateOrderThenReturnOrder() {

            var orderId = "64ec17cefc18541d85df2e27";
            var plate1Id = "64f4d44eb35055bb9b2576b8";
            var plate2Id = "64f4d464b35055bb9b2576b9";
            var plate1Quantity = 2;
            var plate2Quantity = 4;

            var referencePlate1 = PlateGenerator.generator(plate1Id, null, null, plate1Quantity);
            var referencePlate2 = PlateGenerator.generator(plate2Id, null, null, plate2Quantity);
            var order = OrderGenerator.generate(null, Arrays.asList(referencePlate1, referencePlate2), IN_PRODUCTION);

            var plate1Ingredient = IngredientGenerator.generate("Filé de frango",1);
            var plate1Recipe = RecipeGenerator.generate("Filé de grelhado", List.of(plate1Ingredient));
            var plate1 = PlateGenerator.generator(plate1Id, new BigDecimal(40), plate1Recipe, plate1Quantity);

            var plate2Ingredient = IngredientGenerator.generate("Bife de alcatra",1);
            var plate2Recipe = RecipeGenerator.generate("Bife", List.of(plate2Ingredient));
            var plate2 = PlateGenerator.generator(plate2Id, new BigDecimal(60), plate2Recipe, plate2Quantity);

            var orderWithId = OrderGenerator.generate(orderId, List.of(plate1, plate2), IN_PRODUCTION);

            when(plateGateway.searchById(plate1Id)).thenReturn(Optional.of(plate1));
            when(plateGateway.searchById(plate2Id)).thenReturn(Optional.of(plate2));
            when(orderGateway.save(order)).thenReturn(orderWithId);

            var result = orderManager.create(order);

            assertThat(result).isEqualTo(orderWithId);
        }

        @Test
        @DisplayName("When create order with plate there isn't then throw BusinessException")
        public void whenCreateOrderWithPlateThereIsntThenThrowBusinessException() {

            var plate1Id = "64f4d44eb35055bb9b2576b8";
            var plate2Id = "64f5a6cd3be8bd9f7baf2f50";
            var plate1Quantity = 2;
            var plate2Quantity = 4;

            var referencePlate1 = PlateGenerator.generator(plate1Id, null, null, plate1Quantity);
            var referencePlate2 = PlateGenerator.generator(plate2Id, null, null, plate2Quantity);
            var order = OrderGenerator.generate(null, Arrays.asList(referencePlate1, referencePlate2), IN_PRODUCTION);

            var plate1Ingredient = IngredientGenerator.generate("Filé de frango",1);
            var plate1Recipe = RecipeGenerator.generate("Filé de grelhado", List.of(plate1Ingredient));
            var plate1 = PlateGenerator.generator(plate1Id, new BigDecimal(40), plate1Recipe, plate1Quantity);

            when(plateGateway.searchById(plate1Id)).thenReturn(Optional.of(plate1));
            when(plateGateway.searchById(plate2Id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> orderManager.create(order))
                    .isInstanceOf(PlateNotFoundException.class)
                    .hasMessage(String.format("There isn't plate for id %s", plate2Id));

            verifyNoInteractions(orderGateway);
        }

    }

    @DisplayName("Cancel Test")
    @Nested
    class CancelTest {

        @Test
        @DisplayName("When cancel order then change order status to canceled")
        public void whenCancelOrderThenChangeOrderStatusToCanceled() {

            var orderId = "64ec17cefc18541d85df2e27";

            var plateIngredient = IngredientGenerator.generate("Filé de frango",1);
            var plateRecipe = RecipeGenerator.generate("Filé de grelhado", List.of(plateIngredient));
            var plate = PlateGenerator.generator("64f4d44eb35055bb9b2576b8", new BigDecimal(40), plateRecipe, 2);

            var inProductionOrder = OrderGenerator.generate(orderId, List.of(plate), IN_PRODUCTION);
            var canceledOrder = OrderGenerator.generate(orderId, List.of(plate), CANCELED);

            when(orderGateway.search(orderId)).thenReturn(Optional.of(inProductionOrder));

            orderManager.cancel(orderId);

            verify(orderGateway).save(canceledOrder);
        }

        @Test
        @DisplayName("When cancel order there isn't then throw OrderNotFoundException")
        public void whenCancelOrderThereIsntThenThrowOrderNotFoundException() {

            var orderId = "64f5af7a3be8bd9f7baf2f51";

            when(orderGateway.search(orderId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> orderManager.cancel(orderId))
                    .isInstanceOf(OrderNotFoundException.class)
                    .hasMessage(String.format("There isn't order for id %s", orderId));
        }

        @Test
        @DisplayName("When cancel order that is already then throw OrderCantBeCanceledException")
        public void whenCancelOrderThatIsAlreadyThenThrowOrderCantBeCanceledException() {

            var orderId = "64ec17cefc18541d85df2e27";

            var plateIngredient = IngredientGenerator.generate("Filé de frango",1);
            var plateRecipe = RecipeGenerator.generate("Filé de grelhado", List.of(plateIngredient));
            var plate = PlateGenerator.generator("64f4d44eb35055bb9b2576b8", new BigDecimal(40), plateRecipe, 2);

            var readyOrder = OrderGenerator.generate(orderId, List.of(plate), READY);

            when(orderGateway.search(orderId)).thenReturn(Optional.of(readyOrder));

            assertThatThrownBy(() -> orderManager.cancel(orderId))
                    .isInstanceOf(OrderCantBeCanceledException.class)
                    .hasMessage(String.format("The order with id %s cannot be cancelled because it is ready", orderId));
        }

    }

}
