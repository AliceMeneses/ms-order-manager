package com.majella.ordermanager.entrypoint.api.controller.contract;

import com.majella.ordermanager.entrypoint.api.controller.payload.request.OrderRequest;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderManagerContract {

    OrderResponse create(@RequestBody @Valid OrderRequest orderRequest);

    void cancel(@PathVariable String id);

}
