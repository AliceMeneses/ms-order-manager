package com.majella.ordermanager.core.usecase;

import com.majella.ordermanager.core.gateway.PlateGateway;
import com.majella.ordermanager.helper.IngredientGenerator;
import com.majella.ordermanager.helper.PlateGenerator;
import com.majella.ordermanager.helper.RecipeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
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

            var plateId = "64f4d44eb35055bb9b2576b8";
            var plateName = "Filé de frango grelhado";
            var platePrice = new BigDecimal(40);

            var plateIngredient = IngredientGenerator.generate("Filé de frango",1);
            var plateRecipe = RecipeGenerator.generate(plateName, List.of(plateIngredient));
            var plate = PlateGenerator.generator(plateId, platePrice, plateRecipe, 2);

            var page = new PageImpl<>(List.of(plate), pageable, 1);

            when(plateGateway.list(pageable)).thenReturn(page);

            var result = menu.list(pageable);

            assertThat(result).isEqualTo(page);
        }

    }

}