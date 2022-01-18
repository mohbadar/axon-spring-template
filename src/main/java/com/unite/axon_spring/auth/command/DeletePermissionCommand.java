package com.unite.axon_spring.auth.command;

import lombok.Data;

@Data
public class DeletePermissionCommand {
    private final String permissionId;
}
