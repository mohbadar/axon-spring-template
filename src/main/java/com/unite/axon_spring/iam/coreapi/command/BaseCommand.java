package com.unite.axon_spring.iam.coreapi.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.util.Assert;

@Data
public class BaseCommand<T> {

    @TargetAggregateIdentifier
    private final T id;

    public BaseCommand(T id) {
        Assert.notNull(id, "Id must be not null");
        this.id = id;
    }
}