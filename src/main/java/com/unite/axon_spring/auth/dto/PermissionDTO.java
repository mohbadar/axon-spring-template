package com.unite.axon_spring.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class PermissionDTO {
    private String permissionId;
    private String name;
    private String description;
    private boolean active;
}
