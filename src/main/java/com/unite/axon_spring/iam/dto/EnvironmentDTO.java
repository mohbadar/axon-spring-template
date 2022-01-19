package com.unite.axon_spring.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentDTO {

    private String id;
    private String slug;
    private String name;
    private String description;
    private String secretKey;
    private boolean active;


}
