package com.majella.ordermanager.entrypoint.api.controller;

import com.majella.ordermanager.core.exception.BusinessException;
import com.majella.ordermanager.core.exception.PlateNotFoundException;
import com.majella.ordermanager.core.usecase.OrderManager;
import com.majella.ordermanager.entrypoint.api.mapper.OrderMapper;
import com.majella.ordermanager.helper.OrderGenerator;
import com.majella.ordermanager.helper.OrderRequestGenerator;
import com.majella.ordermanager.helper.OrderResponseGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Order Manager Controller Test")
@ExtendWith(MockitoExtension.class)
public class OrderManagerControllerTest {

    @Mock
    private OrderManager orderManager;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderManagerController orderManagerController;

    @Nested
    @DisplayName("Create Order Test")
    class CreateOrderTest {

        @Test
        @DisplayName("When create order then return OrderResponse")
        public void whenCreateOrderThenReturOrderResponse() {

            var orderId = "64ec17cefc18541d85df2e27";
            var plateId = "64ec07ee6e17f2e337a9fbc1";

            var orderRequest = OrderRequestGenerator.generate(plateId);
            var order = OrderGenerator.generate(null, plateId);
            var orderWithId = OrderGenerator.generate(orderId, plateId);
            var orderResponse = OrderResponseGenerator.generate(orderId);

            when(orderMapper.toDomain(orderRequest)).thenReturn(order);
            when(orderManager.create(order)).thenReturn(orderWithId);
            when(orderMapper.toResponse(orderWithId)).thenReturn(orderResponse);

            var result = orderManagerController.create(orderRequest);

            assertThat(result).isEqualTo(orderResponse);
        }

        @Test
        @DisplayName("When create order with plate there isn't throw BusinessException")
        public void whenCreateOrderWithPlateThereIsntThenThrowBusinessException() {

            var plateId = "64f4c8cab35055bb9b2576b7";
            var plateNotFoundException = new PlateNotFoundException(plateId);

            var orderRequest = OrderRequestGenerator.generate(plateId);
            var order = OrderGenerator.generate(null, plateId);

            when(orderMapper.toDomain(orderRequest)).thenReturn(order);
            when(orderManager.create(order)).thenThrow(plateNotFoundException);

            assertThatThrownBy(() -> orderManagerController.create(orderRequest))
                    .isInstanceOf(BusinessException.class)
                    .hasCause(plateNotFoundException)
                    .hasMessage(String.format("There isn't plate for id %s", plateId));
        }

    }

    @Nested
    @DisplayName("Cancel Test")
    class CancelTest {

        @Test
        @DisplayName("When cancel order then cancel order")
        public void whenCancelOrderThenCancelOrder() {

            var id = "64ec17cefc18541d85df2e27";

            orderManagerController.cancel(id);

            verify(orderManager).cancel(id);
        }

    }

}
