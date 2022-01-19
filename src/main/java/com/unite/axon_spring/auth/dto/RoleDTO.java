package com.unite.axon_spring.auth.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private List<String> permissionIds;
}
