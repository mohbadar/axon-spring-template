package com.unite.axon_spring.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private String id;
    private String name;
    private String description;
    private boolean active;
    private String envSlug;
    private List<String> roleIds;
}
