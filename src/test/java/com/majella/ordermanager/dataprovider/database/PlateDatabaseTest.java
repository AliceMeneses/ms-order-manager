package com.majella.ordermanager.dataprovider.database;

import com.majella.ordermanager.dataprovider.database.repository.PlateRepository;
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

            var plate = PlateGenerator.generatorGrilledChickenPlate();

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
            var plate = PlateGenerator.generatorGrilledChickenPlate();

            when(plateRepository.findById(plate.getId())).thenReturn(Optional.of(plate));

            var result = plateDatabase.searchById(plate.getId());

            assertThat(result).isEqualTo(Optional.of(plate));
        }

    }

}
