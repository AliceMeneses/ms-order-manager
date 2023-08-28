package com.majella.ordermanager.core.gateway;

import com.majella.ordermanager.core.domain.Order;

import java.util.Optional;

public interface OrderGateway {

    Order save(Order order);

    Optional<Order> search(String id);

    void update(Order order);

}
