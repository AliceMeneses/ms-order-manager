package com.majella.ordermanager.core.usecase;

import com.majella.ordermanager.core.domain.Order;
import com.majella.ordermanager.core.exception.OrderNotFoundException;
import com.majella.ordermanager.core.exception.PlateNotFoundException;
import com.majella.ordermanager.core.gateway.OrderGateway;
import com.majella.ordermanager.core.gateway.PlateGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderManager {

    private final OrderGateway orderGateway;
    private final PlateGateway plateGateway;

    public Order create(Order order) {

        var plates = order.getPlates();

        for (int i = 0; i < plates.size(); i++) {
            var id = plates.get(i).getId();
            var plate = plateGateway.searchById(id).orElseThrow(() -> new PlateNotFoundException(id));
            plate.setQuantity(plates.get(i).getQuantity());
            plates.set(i, plate);
        }

        return orderGateway.save(order);
    }

    public void cancel(String id) {
        var order = orderGateway.search(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.cancel();
        orderGateway.save(order);
    }

}
