package com.majella.ordermanager.core.usecase;

import com.majella.ordermanager.core.domain.Plate;
import com.majella.ordermanager.core.gateway.PlateGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Menu {

    private final PlateGateway plateGateway;

    public Page<Plate> list(Pageable pageable) {
       return plateGateway.list(pageable);
    }


}
