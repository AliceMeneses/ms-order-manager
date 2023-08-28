package com.majella.ordermanager.dataprovider.database.repository;

import com.majella.ordermanager.core.domain.Plate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlateRepository extends MongoRepository<Plate, String> {
}
