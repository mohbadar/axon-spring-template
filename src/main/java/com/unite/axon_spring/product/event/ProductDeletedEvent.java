package com.unite.axon_spring.product.event;

import lombok.Data;

@Data
public class ProductDeletedEvent {
    private final String id;
}
