package com.majella.ordermanager.core.usecase;

import com.majella.ordermanager.core.gateway.PlateGateway;
import com.majella.ordermanager.helper.PlateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Menu Test")
@ExtendWith(MockitoExtension.class)
public class MenuTest {

    @Mock
    private PlateGateway plateGateway;

    @InjectMocks
    private Menu menu;

    @Nested
    @DisplayName("List menu test")
    class ListMenuTest {

        @Test
        @DisplayName("When get menu plates then return menu plates")
        public void whenGetMenuPlatesThenReturnMenuPlates() {
            var pageable = PageRequest.of(0,1);

            var plate = PlateGenerator.randomlyGenerator();

            var page = new PageImpl<>(List.of(plate), pageable, 1);

            when(plateGateway.list(pageable)).thenReturn(page);

            var result = menu.list(pageable);

            assertThat(result).isEqualTo(page);
        }

    }

}
