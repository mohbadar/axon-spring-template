package com.unite.axon_spring.auth.service;

import com.unite.axon_spring.auth.event.RoleCreatedEvent;
import com.unite.axon_spring.auth.model.Role;
import com.unite.axon_spring.auth.repository.RoleRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @CommandHandler
    public void registerRole(RoleCreatedEvent event)
    {
        Role role = new Role();
        role.setName(event.getRoleDTO().getName());
        role.setDescription(event.getRoleDTO().getDescription());
        role.setActive(true);
        roleRepository.save(role);
    }
}
