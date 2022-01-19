package com.unite.axon_spring.auth.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.AggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPermissionCommand {
    private String permissionId;
    private String name;
    private String description;
    private boolean active;


    @AggregateIdentifier
    private String getAggregateIdentifier() {
        return (null != permissionId) ? permissionId +"": name;
    }
}
