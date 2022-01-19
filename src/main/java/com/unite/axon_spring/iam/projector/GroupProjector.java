package com.unite.axon_spring.iam.projector;

import com.unite.axon_spring.iam.dto.GroupFullViewDTO;
import com.unite.axon_spring.iam.dto.RoleFullViewDTO;
import com.unite.axon_spring.iam.event.*;
import com.unite.axon_spring.iam.exception.EnvironmentNotExistException;
import com.unite.axon_spring.iam.exception.GroupNotExistException;
import com.unite.axon_spring.iam.exception.RoleNotExistException;
import com.unite.axon_spring.iam.mapper.ObjectDtoMapper;
import com.unite.axon_spring.iam.model.Environment;
import com.unite.axon_spring.iam.model.Group;
import com.unite.axon_spring.iam.model.Permission;
import com.unite.axon_spring.iam.model.Role;
import com.unite.axon_spring.iam.query.GetGroupQuery;
import com.unite.axon_spring.iam.query.GetGroupsQuery;
import com.unite.axon_spring.iam.query.GetRoleQuery;
import com.unite.axon_spring.iam.query.GetRolesQuery;
import com.unite.axon_spring.iam.repository.EnvironmentRepository;
import com.unite.axon_spring.iam.repository.GroupRepository;
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
public class GroupProjector {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private EnvironmentRepository environmentRepository;

    @EventHandler
    public void AddGroup(GroupCreatedEvent event)
    {

        Environment environment = environmentRepository.findBySlug(event.getEnvSlug());

        if(environment == null)
            throw new EnvironmentNotExistException();

        List<Role> roles = roleRepository.findAllById(event.getRoleIds());

        Group group = new Group();
        group.setId(event.getId());
        group.setName(event.getName());
        group.setDescription(event.getDescription());
        group.setEnvSlug(event.getEnvSlug());
        group.setActive(event.isActive());
        group.setRoles(roles);

        groupRepository.save(group);
    }


    @EventHandler
    public void updateGroup(GroupUpdatedEvent event)
    {

        Environment environment = environmentRepository.findBySlug(event.getEnvSlug());
        if(environment == null)
            throw new EnvironmentNotExistException();

        List<Role> roles = roleRepository.findAllById(event.getRoleIds());

        Group group = groupRepository.findById(event.getId()).orElse(null);
        if(group == null)
            throw new GroupNotExistException();

        group.setName(event.getName());
        group.setDescription(event.getDescription());
        group.setEnvSlug(event.getEnvSlug());
        group.setActive(event.isActive());
        group.setRoles(roles);

        groupRepository.save(group);
    }


    @EventHandler
    public void deactivateGroup(GroupDeactivatedEvent event)
    {
        Group group = groupRepository.findById(event.getId()).orElse(null);
        if(group == null)
            throw new GroupNotExistException();
        group.setActive(false);
        groupRepository.save(group);
    }

    @QueryHandler
    public GroupFullViewDTO getGroup(GetGroupQuery query)
    {
        Group g = groupRepository.findById(query.getId()).orElse(null);
        if(groupRepository == null)
            throw new GroupNotExistException();
        return ObjectDtoMapper.to(g);
    }

    @QueryHandler
    public List<GroupFullViewDTO> getGroups(GetGroupsQuery query)
    {
        return groupRepository.findAll().stream().map(toGroup()).collect(Collectors.toList());

    }

    private Function<Group, GroupFullViewDTO> toGroup(){
        return r->{
            return ObjectDtoMapper.to(r);
        };
    }
}
