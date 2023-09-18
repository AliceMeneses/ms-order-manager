package com.majella.ordermanager.core.domain;

import com.majella.ordermanager.core.exception.OrderCantBeCanceledException;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document("order")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Order {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private List<Plate> plates;

    @Builder.Default
    private Status status = Status.IN_PRODUCTION;

    public void cancel() {
        if (status == Status.READY) {
            throw new OrderCantBeCanceledException(id);
        }

        status = Status.CANCELED;
    }

}
