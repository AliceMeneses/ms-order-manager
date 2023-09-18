package com.majella.ordermanager.entrypoint.api.controller.payload.response;

import com.majella.ordermanager.core.domain.Status;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderResponse {

    private String id;
    private Status status;
    private List<PlateResponse> plates;
    private BigDecimal totalPrice;

    public void calculateTotalPrice() {
        totalPrice = plates.stream().map(plate -> {

            var quantity = new BigDecimal(plate.getQuantity());
            return plate
                    .getPrice()
                    .multiply(quantity);

        }).reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
