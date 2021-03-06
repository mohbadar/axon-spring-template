package com.unite.axon_spring.iam.eventhadler.projection;

import com.unite.axon_spring.iam.common.dto.UserFullViewDTO;
import com.unite.axon_spring.iam.common.exception.RoleNotExistException;
import com.unite.axon_spring.iam.common.exception.UserNotExistException;
import com.unite.axon_spring.iam.common.mapper.ObjectDtoMapper;
import com.unite.axon_spring.iam.commandmodel.model.Environment;
import com.unite.axon_spring.iam.commandmodel.model.Group;
import com.unite.axon_spring.iam.commandmodel.model.User;
import com.unite.axon_spring.iam.coreapi.*;
import com.unite.axon_spring.iam.eventhadler.repository.EnvironmentRepository;
import com.unite.axon_spring.iam.eventhadler.repository.GroupRepository;
import com.unite.axon_spring.iam.eventhadler.repository.UserRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserProjection {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnvironmentRepository environmentRepository;
    @Autowired
    private GroupRepository groupRepository;

    @EventHandler
    public void addUser(UserCreatedEvent event)
    {
        System.out.println(event.toString());
        List<Environment> environments = environmentRepository.findAllById(event.getEnvironmentIds());
        List<Group> groups = groupRepository.findAllById(event.getGroupIds());

        User user = new User();
        user.setId(event.getId());
        user.setName(event.getName());
        user.setAddress(event.getAddress());
        user.setActive(event.getActive());
        user.setPhoneNo(event.getPhoneNo());
        user.setUsername(event.getUsername());

        //cypher data to be stored
        user.setPassword(encodePassword(event.getPassword().trim()));

        user.setEmail(event.getEmail());
        user.setGroups(groups);
        user.setEnvironments(environments);

        userRepository.save(user);
    }


    @EventHandler
    public void updateUser(UserUpdatedEvent event)
    {
        System.out.println(event.toString());
        List<Environment> environments = environmentRepository.findAllById(event.getEnvironmentIds());
        List<Group> groups = groupRepository.findAllById(event.getGroupIds());

        User user = userRepository.findById(event.getId()).orElse(null);

        if(user ==null )
            throw new UserNotExistException();

        user.setId(event.getId());
        user.setName(event.getName());
        user.setAddress(event.getAddress());
        user.setActive(event.getActive());
        user.setPhoneNo(event.getPhoneNo());
        user.setUsername(event.getUsername());

        //cypher data to be stored
        user.setPassword(encodePassword(event.getPassword().trim()));

        user.setEmail(event.getEmail());
        user.setGroups(groups);
        user.setEnvironments(environments);

        userRepository.save(user);
    }

    @EventHandler
    public void deactivateUser(UserDeactivatedEvent event)
    {
        User user = userRepository.findById(event.getId()).orElse(null);

        if(user ==null )
            throw new UserNotExistException();

        user.setActive(false);
        userRepository.save(user);
    }


    @QueryHandler
    public UserFullViewDTO getUserById(GetUserByIdQuery query)
    {
        User user = userRepository.findById(query.getId()).orElse(null);
        if(user == null)
            throw new RoleNotExistException();
        return ObjectDtoMapper.to(user);
    }


    @QueryHandler
    public UserFullViewDTO getUserByUsername(GetUserByUsernameQuery query)
    {
        User user = userRepository.findByUsername(query.getUsername());
        if(user == null)
            throw new RoleNotExistException();
        return ObjectDtoMapper.to(user);
    }

    @QueryHandler
    public UserFullViewDTO getUserByEmail(GetUserByEmailQuery query)
    {
        User user = userRepository.findByEmail(query.getEmail()).orElse(null);
        if(user == null)
            throw new RoleNotExistException();
        return ObjectDtoMapper.to(user);
    }

    @QueryHandler
    public List<UserFullViewDTO> getUsers(GetRolesQuery query)
    {
        List<UserFullViewDTO> users = userRepository.findAll().stream().map(toUser()).collect(Collectors.toList());
        return users;
    }


    @QueryHandler
    public List<UserFullViewDTO> getUsersByEnvSlug(GetUserByEnvQuery query)
    {
        List<UserFullViewDTO> users = userRepository.findAllByEnvSlug(query.getEnvSlug()).stream().map(toUser()).collect(Collectors.toList());
        return users;
    }

    private Function<User, UserFullViewDTO> toUser(){
        return r->{
            return ObjectDtoMapper.to(r);
        };
    }



    public String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
