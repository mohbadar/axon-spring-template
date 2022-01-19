package com.unite.axon_spring.iam.projector;

import com.unite.axon_spring.iam.dto.EnvironmentDTO;
import com.unite.axon_spring.iam.dto.RoleDTO;
import com.unite.axon_spring.iam.dto.RoleFullViewDTO;
import com.unite.axon_spring.iam.event.RoleCreatedEvent;
import com.unite.axon_spring.iam.event.RoleDeactivatedEvent;
import com.unite.axon_spring.iam.event.RoleUpdatedEvent;
import com.unite.axon_spring.iam.exception.EnvironmentNotExistException;
import com.unite.axon_spring.iam.exception.RoleNotExistException;
import com.unite.axon_spring.iam.mapper.ObjectDtoMapper;
import com.unite.axon_spring.iam.model.Environment;
import com.unite.axon_spring.iam.model.Permission;
import com.unite.axon_spring.iam.model.Role;
import com.unite.axon_spring.iam.query.GetEnvironmentsQuery;
import com.unite.axon_spring.iam.query.GetRoleQuery;
import com.unite.axon_spring.iam.query.GetRolesQuery;
import com.unite.axon_spring.iam.repository.EnvironmentRepository;
import com.unite.axon_spring.iam.repository.PermissionRepository;
import com.unite.axon_spring.iam.repository.RoleRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoleProjector {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private EnvironmentRepository environmentRepository;

    @EventHandler
    public void addRole(RoleCreatedEvent event)
    {

        Environment environment = environmentRepository.findBySlug(event.getEnvSlug());

        if(environment == null)
            throw new EnvironmentNotExistException();

        List<Permission> permissions = permissionRepository.findAllById(event.getPermissionIds());

        Role role = new Role();
        role.setId(event.getId());
        role.setName(event.getName());
        role.setDescription(event.getDescription());
        role.setEnvSlug(event.getEnvSlug());
        role.setActive(event.isActive());
        role.setPermissions(permissions);

        roleRepository.save(role);
    }


    @EventHandler
    public void updateRole(RoleUpdatedEvent event)
    {

        Environment environment = environmentRepository.findBySlug(event.getEnvSlug());
        if(environment == null)
            throw new EnvironmentNotExistException();

        List<Permission> permissions = permissionRepository.findAllById(event.getPermissionIds());

        Role role = roleRepository.findById(event.getId()).orElse(null);
        if(role == null)
            throw new RoleNotExistException();

        role.setId(event.getId());
        role.setName(event.getName());
        role.setDescription(event.getDescription());
        role.setEnvSlug(event.getEnvSlug());
        role.setActive(event.isActive());
        role.setPermissions(permissions);

        roleRepository.save(role);
    }


    @EventHandler
    public void deactivateRole(RoleDeactivatedEvent event)
    {
        Role role = roleRepository.findById(event.getId()).orElse(null);
        if(role == null)
            throw new RoleNotExistException();
        role.setActive(false);
        roleRepository.save(role);
    }

    @QueryHandler
    public RoleFullViewDTO getRole(GetRoleQuery query)
    {
        Role role = roleRepository.findById(query.getId()).orElse(null);
        if(role == null)
            throw new RoleNotExistException();
        return ObjectDtoMapper.to(role);
    }

    @QueryHandler
    public List<RoleFullViewDTO> getRoles(GetRolesQuery query)
    {
        List<RoleFullViewDTO> roles = roleRepository.findAll().stream().map(toRole()).collect(Collectors.toList());
        return roles;
    }

    private Function<Role, RoleFullViewDTO> toRole(){
        return r->{
            return ObjectDtoMapper.to(r);
        };
    }
}
