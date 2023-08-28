package com.majella.ordermanager.entrypoint.api.mapper;

import com.majella.ordermanager.core.domain.Plate;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.MenuPlateResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuMapper {

    private final ModelMapper modelMapper;

    public List<MenuPlateResponse> toCollectionResponse(List<Plate> plates) {
       return plates.stream().map(this::toResponse).toList();
    }

    private MenuPlateResponse toResponse(Plate plate) {
        return modelMapper.map(plate, MenuPlateResponse.class);
    }

}
