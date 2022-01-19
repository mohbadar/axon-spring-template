package com.unite.axon_spring.auth.command;

import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;

@Data
public class DeletePermissionCommand {
    private final String permissionId;

    @AggregateIdentifier
    private String getAggregateIdentifier() {
        return permissionId;
    }
}
