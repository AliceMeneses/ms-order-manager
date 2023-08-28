package com.majella.ordermanager.entrypoint.api.config;

import com.majella.ordermanager.core.domain.Plate;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.MenuPlateResponse;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.PlateResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Plate.class, MenuPlateResponse.class)
                .addMappings(mapper -> mapper.map(plate -> plate.getRecipe().getName(), MenuPlateResponse::setName));

        modelMapper.createTypeMap(Plate.class, PlateResponse.class)
                .addMappings(mapper -> mapper.map(plate -> plate.getRecipe().getName(), PlateResponse::setName));

        return modelMapper;
    }

}
