package com.majella.ordermanager.entrypoint.api.controller;

import com.majella.ordermanager.core.usecase.Menu;
import com.majella.ordermanager.entrypoint.api.controller.contract.MenuContract;
import com.majella.ordermanager.entrypoint.api.controller.payload.response.MenuPlateResponse;
import com.majella.ordermanager.entrypoint.api.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/menu")
@RequiredArgsConstructor
public class MenuController implements MenuContract {

    private final Menu menu;
    private final MenuMapper menuMapper;

    @GetMapping
    @Override
    public List<MenuPlateResponse> list(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        var plates = menu.list(pageable);
        return menuMapper.toCollectionResponse(plates.getContent());
    }

}
