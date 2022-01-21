package com.unite.axon_spring.iam.eventhadler.projection;

import com.unite.axon_spring.iam.common.dto.EnvironmentDTO;
import com.unite.axon_spring.iam.coreapi.event.EnvironmentCreatedEvent;
import com.unite.axon_spring.iam.coreapi.event.EnvironmentDeactivatedEvent;
import com.unite.axon_spring.iam.coreapi.event.EnvironmentUpdatedEvent;
import com.unite.axon_spring.iam.common.exception.EnvironmentNotExistException;
import com.unite.axon_spring.iam.common.mapper.ObjectDtoMapper;
import com.unite.axon_spring.iam.commandmodel.model.Environment;
import com.unite.axon_spring.iam.coreapi.query.GetEnvironmentQuery;
import com.unite.axon_spring.iam.coreapi.query.GetEnvironmentsQuery;
import com.unite.axon_spring.iam.eventhadler.repository.EnvironmentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EnvironmentProjection {
    private final EnvironmentRepository environmentRepository;

    @Autowired
    public EnvironmentProjection(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
    }

    @EventHandler
    public void createEnvironment(EnvironmentCreatedEvent event)
    {
        Environment environment = new Environment();
        environment.setId(event.getId());
        environment.setSlug(event.getSlug());
        environment.setName(event.getName());
        environment.setDescription(event.getDescription());
        environment.setSecretKey(event.getSecretKey());
        environment.setActive(event.isActive());
        environmentRepository.save(environment);
    }

    @EventHandler
    public void updateEnvironment(EnvironmentUpdatedEvent event)
    {
        Environment environment = new Environment();
        environment.setId(event.getId());
        environment.setSlug(event.getSlug());
        environment.setName(event.getName());
        environment.setDescription(event.getDescription());
        environment.setSecretKey(event.getSecretKey());
        environment.setActive(event.isActive());
        environmentRepository.save(environment);
    }

    @EventHandler
    public void deactivateEnvironment(EnvironmentDeactivatedEvent event)
    {
        Environment environment = environmentRepository.findById(event.getId()).orElse(null);

        if(environment == null)
            throw new EnvironmentNotExistException();

        environment.setActive(false);
        environmentRepository.save(environment);
    }

    @QueryHandler
    public EnvironmentDTO getEnvironment(GetEnvironmentQuery query)
    {
        Environment environment = environmentRepository.findById(query.getId()).orElse(null);

        if(environment == null)
            throw new EnvironmentNotExistException();

        return ObjectDtoMapper.to(environment);
    }

    @QueryHandler
    public List<EnvironmentDTO> getEnvironments(GetEnvironmentsQuery query)
    {
        List<EnvironmentDTO> environments = environmentRepository.findAll().stream().map(toEnvironment()).collect(Collectors.toList());

        return environments;
    }

    private Function<Environment, EnvironmentDTO> toEnvironment() {
        return e -> {
            return ObjectDtoMapper.to(e);
        };
    }
}
