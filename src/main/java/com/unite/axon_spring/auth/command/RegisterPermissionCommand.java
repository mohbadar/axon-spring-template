package com.unite.axon_spring.auth.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPermissionCommand {
    private String permissionId;
    private String name;
    private String description;
    private boolean active;
}
