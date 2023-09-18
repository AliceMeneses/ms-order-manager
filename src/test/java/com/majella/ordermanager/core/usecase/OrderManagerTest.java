package com.majella.ordermanager.core.usecase;

import com.github.javafaker.Faker;
import com.majella.ordermanager.core.exception.PlateNotFoundException;
import com.majella.ordermanager.core.exception.OrderCantBeCanceledException;
import com.majella.ordermanager.core.exception.OrderNotFoundException;
import com.majella.ordermanager.core.gateway.OrderGateway;
import com.majella.ordermanager.core.gateway.PlateGateway;
import com.majella.ordermanager.helper.FakerJavaUtil;
import com.majella.ordermanager.helper.OrderGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private final Faker FAKER = FakerJavaUtil.getFaker();

    @DisplayName("Create Test")
    @Nested
    class CreateTest {

        @Test
        @DisplayName("When create order then return order")
        public void whenCreateOrderThenReturnOrder() {

            var order = OrderGenerator.generateWithReferenceToTheTwoPlates();
            var referenceToTheGrilledChickenPlate = order.getPlates().get(0);
            var referenceToTheBeefPlate = order.getPlates().get(1);

            var orderWithId = OrderGenerator.generateWithTwoPlates(FAKER.number().digit(), IN_PRODUCTION);
            var grilledChickenPlate = orderWithId.getPlates().get(0);
            var beefPlate = orderWithId.getPlates().get(1);

            when(plateGateway.searchById(referenceToTheGrilledChickenPlate.getId())).thenReturn(Optional.of(grilledChickenPlate));
            when(plateGateway.searchById(referenceToTheBeefPlate.getId())).thenReturn(Optional.of(beefPlate));
            when(orderGateway.save(order)).thenReturn(orderWithId);

            var result = orderManager.create(order);

            assertThat(result).isEqualTo(orderWithId);
        }

        @Test
        @DisplayName("When create order with plate there isn't then throw BusinessException")
        public void whenCreateOrderWithPlateThereIsntThenThrowBusinessException() {

            var order = OrderGenerator.generateWithReferenceToTheTwoPlates();
            var referenceToTheGrilledChickenPlate = order.getPlates().get(0);
            var referenceToTheBeefPlate = order.getPlates().get(1);

            var orderWithId = OrderGenerator.generateWithTwoPlates(FAKER.number().digit(), IN_PRODUCTION);
            var grilledChickenPlate = orderWithId.getPlates().get(0);

            when(plateGateway.searchById(referenceToTheGrilledChickenPlate.getId())).thenReturn(Optional.of(grilledChickenPlate));
            when(plateGateway.searchById(referenceToTheBeefPlate.getId())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> orderManager.create(order))
                    .isInstanceOf(PlateNotFoundException.class)
                    .hasMessage(String.format("There isn't plate for id %s", referenceToTheBeefPlate.getId()));

            verifyNoInteractions(orderGateway);
        }

    }

    @DisplayName("Cancel Test")
    @Nested
    class CancelTest {

        @Test
        @DisplayName("When cancel order then change order status to canceled")
        public void whenCancelOrderThenChangeOrderStatusToCanceled() {
            var orderId = FAKER.number().digit();
            var inProductionOrder = OrderGenerator.generate(orderId,  IN_PRODUCTION);

            when(orderGateway.search(orderId)).thenReturn(Optional.of(inProductionOrder));

            orderManager.cancel(orderId);

            inProductionOrder.setStatus(CANCELED);
            verify(orderGateway).save(inProductionOrder);
        }

        @Test
        @DisplayName("When cancel order there isn't then throw OrderNotFoundException")
        public void whenCancelOrderThereIsntThenThrowOrderNotFoundException() {

            var orderId = FAKER.number().digit();

            when(orderGateway.search(orderId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> orderManager.cancel(orderId))
                    .isInstanceOf(OrderNotFoundException.class)
                    .hasMessage(String.format("There isn't order for id %s", orderId));
        }

        @Test
        @DisplayName("When cancel order that is already then throw OrderCantBeCanceledException")
        public void whenCancelOrderThatIsAlreadyThenThrowOrderCantBeCanceledException() {

            var readyOrder = OrderGenerator.generate(FAKER.number().digit(), READY);

            when(orderGateway.search(readyOrder.getId())).thenReturn(Optional.of(readyOrder));

            assertThatThrownBy(() -> orderManager.cancel(readyOrder.getId()))
                    .isInstanceOf(OrderCantBeCanceledException.class)
                    .hasMessage(String.format("The order with id %s cannot be cancelled because it is ready", readyOrder.getId()));
        }

    }

}
