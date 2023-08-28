package com.majella.ordermanager.entrypoint.api.mapper;

import com.majella.ordermanager.core.domain.Order;
import com.majella.ordermanager.entrypoint.api.controller.payload.request.OrderRequest;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public Order toDomain(OrderRequest orderRequest) {
       return modelMapper.map(orderRequest, Order.class);
    }

    public OrderResponse toResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }

}
