package com.majella.ordermanager.dataprovider.database.repository;

import com.majella.ordermanager.core.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

}
