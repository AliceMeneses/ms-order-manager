package com.majella.ordermanager.entrypoint.api.controller.contract;

import com.majella.ordermanager.entrypoint.api.controller.payload.response.MenuPlateResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuContract {

    List<MenuPlateResponse> list(Pageable pageable);

}
