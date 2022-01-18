package com.unite.axon_spring.auth.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PermissionCreatedEvent {
    private final String name;
    private final String description;
    private final boolean active;
}
