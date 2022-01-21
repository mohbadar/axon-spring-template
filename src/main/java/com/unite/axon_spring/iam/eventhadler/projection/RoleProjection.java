package com.unite.axon_spring.iam.eventhadler.projection;

import com.unite.axon_spring.iam.restapi.resource.RoleVO;
import com.unite.axon_spring.iam.coreapi.event.RoleCreatedEvent;
import com.unite.axon_spring.iam.coreapi.event.RoleDeactivatedEvent;
import com.unite.axon_spring.iam.coreapi.event.RoleUpdatedEvent;
import com.unite.axon_spring.iam.common.exception.EnvironmentNotExistException;
import com.unite.axon_spring.iam.common.exception.RoleNotExistException;
import com.unite.axon_spring.iam.common.mapper.ObjectDtoMapper;
import com.unite.axon_spring.iam.commandmodel.model.Environment;
import com.unite.axon_spring.iam.commandmodel.model.Permission;
import com.unite.axon_spring.iam.commandmodel.model.Role;
import com.unite.axon_spring.iam.coreapi.query.GetRoleQuery;
import com.unite.axon_spring.iam.coreapi.query.GetRolesQuery;
import com.unite.axon_spring.iam.eventhadler.repository.EnvironmentRepository;
import com.unite.axon_spring.iam.eventhadler.repository.PermissionRepository;
import com.unite.axon_spring.iam.eventhadler.repository.RoleRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RoleProjection {
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
    public RoleVO getRole(GetRoleQuery query)
    {
        Role role = roleRepository.findById(query.getId()).orElse(null);
        if(role == null)
            throw new RoleNotExistException();
        return ObjectDtoMapper.toRoleVO(role);
    }

    @QueryHandler
    public List<RoleVO> getRoles(GetRolesQuery query)
    {
        List<RoleVO> roles = roleRepository.findAll().stream().map(toRoleVO()).collect(Collectors.toList());
        return roles;
    }
    private Function<Role, RoleVO> toRoleVO(){
        return r->{
            return ObjectDtoMapper.toRoleVO(r);
        };
    }
}
