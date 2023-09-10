package com.majella.ordermanager.dataprovider.database;

import com.majella.ordermanager.dataprovider.database.repository.PlateRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Plate Database Test")
@ExtendWith(MockitoExtension.class)
public class PlateDatabaseTest {

    @Mock
    private PlateRepository plateRepository;

    @InjectMocks
    private PlateDatabase plateDatabase;

    @Nested
    @DisplayName("List plate test")
    class ListPlateTest {

        @Test
        @DisplayName("When list plates then return a page of plates")
        public void whenListPlatesThenReturnAPageOfPlates() {
            var pageable = PageRequest.of(0,1);

            var plateId = "64f4d44eb35055bb9b2576b8";
            var plateName = "Filé de frango grelhado";
            var platePrice = new BigDecimal(40);

            var plateIngredient = IngredientGenerator.generate("Filé de frango",1);
            var plateRecipe = RecipeGenerator.generate(plateName, List.of(plateIngredient));
            var plate = PlateGenerator.generator(plateId, platePrice, plateRecipe, 2);

            var page = new PageImpl<>(List.of(plate), pageable, 1);

            when(plateRepository.findAll(pageable)).thenReturn(page);

            var result = plateDatabase.list(pageable);

            assertThat(result).isEqualTo(page);
        }

    }

    @Nested
    @DisplayName("Search plate by id test")
    class SearchPlateByIdTest {

        @Test
        @DisplayName("When list plates then return a page of plates")
        public void whenListPlatesThenReturnAPageOfPlates() {
            var plateId = "64f4d44eb35055bb9b2576b8";

            var plateIngredient = IngredientGenerator.generate("Filé de frango",1);
            var plateRecipe = RecipeGenerator.generate("Filé de frango grelhado", List.of(plateIngredient));
            var plate = PlateGenerator.generator(plateId, new BigDecimal(40), plateRecipe, 2);

            when(plateRepository.findById(plateId)).thenReturn(Optional.of(plate));

            var result = plateDatabase.searchById(plateId);

            assertThat(result).isEqualTo(Optional.of(plate));
        }

    }

}
