package com.unite.axon_spring.auth.command;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpdatePermissionCommand {
    private final String permissionId;
    private final String name;
    private final String description;
    private final boolean active;
}
