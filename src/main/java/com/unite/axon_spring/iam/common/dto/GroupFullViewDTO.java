package com.unite.axon_spring.iam.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupFullViewDTO {
    private String id;
    private String name;
    private String description;
    private boolean active;
    private String envSlug;
    private List<String> roles;
}
