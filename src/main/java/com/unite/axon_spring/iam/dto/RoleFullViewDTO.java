package com.unite.axon_spring.iam.dto;

import com.unite.axon_spring.iam.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleFullViewDTO {

    private String id;
    private String name;
    private String description;
    private boolean active;
    private String envSlug;
    private Collection<String> permissions;
}
