package com.unite.axon_spring.auth.event;

import lombok.Data;

@Data
public class PermissionUpdatedEvent {
    private final String permissionId;
    private final String name;
    private final String description;
    private final boolean active;
}
