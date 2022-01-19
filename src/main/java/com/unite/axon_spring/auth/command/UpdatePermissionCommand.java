package com.unite.axon_spring.auth.command;

import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.AggregateIdentifier;

@Data
@ToString
public class UpdatePermissionCommand {
    private final String permissionId;
    private final String name;
    private final String description;
    private final boolean active;

    @AggregateIdentifier
    private String getAggregateIdentifier() {
        return (null != permissionId) ? permissionId +"": name;
    }
}
