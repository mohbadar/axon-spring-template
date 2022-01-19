package com.unite.axon_spring.product.event;

import lombok.Data;

@Data
public class ProductAddedEvent {
    private final String id;
    private final Double price;
    private final Integer stock;
    private final String description;
}
