package com.majella.ordermanager.dataprovider.database;

import com.github.javafaker.Faker;
import com.majella.ordermanager.dataprovider.database.repository.OrderRepository;
import com.majella.ordermanager.helper.FakerJavaUtil;
import com.majella.ordermanager.helper.OrderGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.majella.ordermanager.core.domain.Status.IN_PRODUCTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Order Database Test")
@ExtendWith(MockitoExtension.class)
public class OrderDatabaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderDatabase orderDatabase;

    private final Faker FAKER = FakerJavaUtil.getFaker();

    @Nested
    @DisplayName("Save Test")
    class SaveTest {

        @Test
        @DisplayName("When save order then save")
        public void whenSaveOrderThenReturnOrder() {
            var order = OrderGenerator.generate(null, IN_PRODUCTION);
            var orderWithId = OrderGenerator.generate(FAKER.number().digit(), IN_PRODUCTION);

            when(orderRepository.save(order)).thenReturn(orderWithId);

            var result = orderDatabase.save(order);

            assertThat(result).isEqualTo(orderWithId);
        }

    }

    @Nested
    @DisplayName("Search Test")
    class SearchTest {

        @Test
        @DisplayName("When search order by id then return order")
        public void whenSearchOrderByIdThenReturnOrder() {
            var order = OrderGenerator.generate(FAKER.number().digit(), IN_PRODUCTION);

            when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

            var result = orderDatabase.search(order.getId());

            assertThat(result.isPresent()).isTrue();
            assertThat(result.get()).isEqualTo(order);
        }

    }

}
