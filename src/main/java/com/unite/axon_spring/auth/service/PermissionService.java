package com.unite.axon_spring.auth.service;

import com.unite.axon_spring.auth.event.PermissionCreatedEvent;
import com.unite.axon_spring.auth.model.Permission;
import com.unite.axon_spring.auth.repository.PermissionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
