package com.majella.ordermanager.entrypoint.api.controller;

import com.majella.ordermanager.core.exception.BusinessException;
import com.majella.ordermanager.core.exception.PlateNotFoundException;
import com.majella.ordermanager.core.usecase.OrderManager;
import com.majella.ordermanager.entrypoint.api.controller.contract.OrderManagerContract;
import com.majella.ordermanager.entrypoint.api.controller.payload.request.OrderRequest;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.OrderResponse;
import com.majella.ordermanager.entrypoint.api.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderManagerController implements OrderManagerContract {

    private final OrderManager orderManager;
    private final OrderMapper orderMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public OrderResponse create(OrderRequest orderRequest) {

        try {
            var order = orderMapper.toDomain(orderRequest);
            order = orderManager.create(order);

            var orderResponse = orderMapper.toResponse(order);
            orderResponse.calculateTotalPrice();

            return orderResponse;
        } catch (PlateNotFoundException ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }

    }

    @PutMapping("/{id}/canceled")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void cancel(String id) {
        orderManager.cancel(id);
    }

}
