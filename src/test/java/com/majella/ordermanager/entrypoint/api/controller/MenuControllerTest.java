package com.majella.ordermanager.entrypoint.api.controller;

import com.majella.ordermanager.core.usecase.Menu;
import com.majella.ordermanager.entrypoint.api.mapper.MenuMapper;
import com.majella.ordermanager.helper.MenuPlateResponseGenerator;
import com.majella.ordermanager.helper.PageableGenerator;
import com.majella.ordermanager.helper.PlateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Menu Controller Test")
@ExtendWith(MockitoExtension.class)
public class MenuControllerTest {

    @Mock
    private Menu menu;

    @Mock
    private MenuMapper menuMapper;

    @InjectMocks
    private MenuController menuController;

    @Nested
    @DisplayName("List menu test")
    class ListMenuTest {

        @Test
        @DisplayName("When get menu plates then return menu plates")
        public void whenGetMenuPlatesThenReturnMenuPlates() {
            var pageable = PageableGenerator.generate();

            var plate = PlateGenerator.generatorGrilledChickenPlate();

            var page = new PageImpl<>(List.of(plate), pageable, 1);

            var menuPlateResponse = MenuPlateResponseGenerator.generateWithGrilledChicken();

            when(menu.list(pageable)).thenReturn(page);
            when(menuMapper.toCollectionResponse(page.getContent())).thenReturn(List.of(menuPlateResponse));

            var result = menuController.list(pageable);

            assertThat(result).isEqualTo(List.of(menuPlateResponse));
        }

    }

}
