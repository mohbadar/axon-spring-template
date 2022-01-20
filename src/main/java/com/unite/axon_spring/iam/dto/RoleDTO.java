package com.unite.axon_spring.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private String id;
    @NotNull
    @Size(min=3, message="Name should have at least 3 characters")
    private String name;
    private String description;
    private boolean active;
    @NotNull
    @Size(min=2, message="envSlug should have at least 2 characters")
    private String envSlug;
    private List<String> permissionIds;
}
