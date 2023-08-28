package com.majella.ordermanager.core.gateway;

import com.majella.ordermanager.core.domain.Plate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlateGateway {

    Page<Plate> list(Pageable pageable);

    Optional<Plate> searchById(String id);

}
