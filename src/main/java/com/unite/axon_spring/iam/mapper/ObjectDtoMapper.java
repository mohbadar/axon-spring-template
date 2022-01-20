package com.unite.axon_spring.iam.mapper;

import com.unite.axon_spring.iam.dto.*;
import com.unite.axon_spring.iam.model.*;

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


    public static GroupFullViewDTO to(Group group)
    {
        GroupFullViewDTO dto= new GroupFullViewDTO();
        List<String> roleNames = group.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setEnvSlug(group.getEnvSlug());
        dto.setDescription(group.getDescription());
        dto.setRoles(roleNames);
        return dto;
    }


    public static UserFullViewDTO to(User user)
    {
        List<String> groupNames = user.getGroups().stream().map(Group::getName).collect(Collectors.toList());
        List<String> envNames = user.getEnvironments().stream().map(Environment::getName).collect(Collectors.toList());

        UserFullViewDTO dto= new UserFullViewDTO();
        dto.setName(user.getName());
        dto.setAddress(user.getAddress());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhoneNo(user.getPhoneNo());
        dto.setActive(user.isActive());
        dto.setGroups(groupNames);
        dto.setEnvironments(envNames);

        return dto;
    }


}