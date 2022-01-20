package com.unite.axon_spring.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentDTO {

    private String id;
    @NotNull
    @Size(min=2, message="Slug should have at least 2 characters")
    private String slug;
    @NotNull
    @Size(min=3, message="Name should have at least 3 characters")
    private String name;
    private String description;
    @NotNull
    @Size(min=3, message="SecretKey should have at least 3 characters")
    private String secretKey;
    private boolean active;


}
