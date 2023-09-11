package com.majella.ordermanager.entrypoint.api.controller.payload.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @Valid
    @NotNull
    @Size(min = 1)
    private List<PlateRequest> plates;

}
