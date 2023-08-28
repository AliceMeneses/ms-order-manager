package com.majella.ordermanager.dataprovider.database;

import com.majella.ordermanager.core.domain.Order;
import com.majella.ordermanager.core.gateway.OrderGateway;
import com.majella.ordermanager.dataprovider.database.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDatabase implements OrderGateway {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> search(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

}
