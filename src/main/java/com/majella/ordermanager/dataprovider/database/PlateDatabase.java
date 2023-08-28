package com.majella.ordermanager.dataprovider.database;

import com.majella.ordermanager.core.domain.Plate;
import com.majella.ordermanager.core.gateway.PlateGateway;
import com.majella.ordermanager.dataprovider.database.repository.PlateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlateDatabase implements PlateGateway {

    private final PlateRepository plateRepository;

    @Override
    public Page<Plate> list(Pageable pageable) {
        return plateRepository.findAll(pageable);
    }

    @Override
    public Optional<Plate> searchById(String id) {
        return plateRepository.findById(id);
    }

}
