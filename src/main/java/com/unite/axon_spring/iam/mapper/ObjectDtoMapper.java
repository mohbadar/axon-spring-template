package com.unite.axon_spring.iam.mapper;

import com.unite.axon_spring.iam.dto.EnvironmentDTO;
import com.unite.axon_spring.iam.model.Environment;

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
}
