package com.unite.axon_spring.auth.service;

import com.unite.axon_spring.auth.dto.PermissionDTO;
import com.unite.axon_spring.auth.event.PermissionCreatedEvent;
import com.unite.axon_spring.auth.event.PermissionDeletedEvent;
import com.unite.axon_spring.auth.model.Permission;
import com.unite.axon_spring.auth.query.GetPermissionByIdQuery;
import com.unite.axon_spring.auth.query.GetPermissionsQuery;
import com.unite.axon_spring.auth.repository.PermissionRepository;
import com.unite.axon_spring.library.dto.BookDTO;
import com.unite.axon_spring.library.entity.BookEntity;
import com.unite.axon_spring.library.query.GetBooksQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @EventHandler
    public void addPermission(PermissionCreatedEvent event)
    {
        Permission permission = new Permission();
        permission.setName(event.getName());
        permission.setDescription(event.getDescription());
        permission.setActive(event.isActive());
        permissionRepository.save(permission);
    }

    @EventHandler
    public void updatePermission(PermissionCreatedEvent event)
    {
        Permission permission = new Permission();
        permission.setId(event.getPermissionId());
        permission.setName(event.getName());
        permission.setDescription(event.getDescription());
        permission.setActive(event.isActive());
        permissionRepository.save(permission);
    }

    @EventHandler
    public void deletePermission(PermissionDeletedEvent event)
    {
        permissionRepository.deleteById(event.getPermissionId());
    }

    @QueryHandler
    public List<PermissionDTO> getPermissions(GetPermissionsQuery query)
    {
        return permissionRepository.findAll().stream().map(toPermission()).collect(Collectors.toList());
    }

    private Function<Permission, PermissionDTO> toPermission() {
        return e -> {
            PermissionDTO permission = new PermissionDTO();
            permission.setName(e.getName());
            permission.setDescription(e.getDescription());
            permission.setActive(e.isActive());
            return permission;
        };
    }

    @QueryHandler
    public PermissionDTO getPermission(GetPermissionByIdQuery query)
    {
        Permission permission = permissionRepository.findById(query.getPermissionId()).orElse(null);
        return to(permission);
    }

    private PermissionDTO to(Permission permission)
    {
        PermissionDTO dto = new PermissionDTO();
        dto.setName(permission.getName());
        dto.setActive(permission.isActive());
        dto.setDescription(permission.getDescription());
        return dto;
    }

}
