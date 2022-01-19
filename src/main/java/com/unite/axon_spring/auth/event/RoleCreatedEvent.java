package com.unite.axon_spring.auth.event;

import com.unite.axon_spring.auth.dto.RoleDTO;
import lombok.Data;

@Data
public class RoleCreatedEvent {
    private final RoleDTO roleDTO;
}
