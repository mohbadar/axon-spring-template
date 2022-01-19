package com.unite.axon_spring.product.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class DeleteProductCommand {
    @TargetAggregateIdentifier
    private final String id;
}
