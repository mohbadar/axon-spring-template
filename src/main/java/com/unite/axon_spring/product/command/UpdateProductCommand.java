package com.unite.axon_spring.product.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class UpdateProductCommand {
    @TargetAggregateIdentifier
    private final String id;
    private final Double price;
    private final Integer stock;
    private final String description;
}
