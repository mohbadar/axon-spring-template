package com.unite.axon_spring.auth.event;

import lombok.Data;

@Data
public class PermissionDeletedEvent {
    private final String permissionId;
}
