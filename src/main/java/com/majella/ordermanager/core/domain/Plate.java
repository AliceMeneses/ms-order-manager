package com.majella.ordermanager.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Document("plate")
@Getter
@Setter
public class Plate {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private BigDecimal price;
    private Recipe recipe;
    private Integer quantity;

}
