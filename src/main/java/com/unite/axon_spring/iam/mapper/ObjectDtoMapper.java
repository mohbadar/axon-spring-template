package com.unite.axon_spring.iam.mapper;

import com.unite.axon_spring.iam.dto.EnvironmentDTO;
import com.unite.axon_spring.iam.dto.RoleDTO;
import com.unite.axon_spring.iam.dto.RoleFullViewDTO;
import com.unite.axon_spring.iam.model.Environment;
import com.unite.axon_spring.iam.model.Permission;
import com.unite.axon_spring.iam.model.Role;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectDtoMapper {

    public static EnvironmentDTO to(Environment environment)
    {
        EnvironmentDTO dto= new EnvironmentDTO();
        dto.setId(environment.getId());
        dto.setName(environment.getName());
        dto.setSlug(environment.getSlug());
        dto.setDescription(environment.getDescription());
        return dto;
    }

    public static RoleFullViewDTO to(Role role)
    {
        RoleFullViewDTO dto= new RoleFullViewDTO();
        List<String> permissionNames = role.getPermissions().stream().map(Permission::getName).collect(Collectors.toList());
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setEnvSlug(role.getEnvSlug());
        dto.setDescription(role.getDescription());
        dto.setPermissions(permissionNames);
        return dto;
    }

}
