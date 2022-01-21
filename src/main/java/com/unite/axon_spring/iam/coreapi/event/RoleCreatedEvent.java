package com.unite.axon_spring.iam.coreapi.event;

import lombok.Data;

import java.util.List;

@Data
public class RoleCreatedEvent extends BaseEvent<String>{
    private final String name;
    private final String description;
    private final boolean active;
    private final String envSlug;
    private List<String> permissionIds;

    public RoleCreatedEvent(String id, String name, String description, boolean active, String envSlug, List<String> permissionIds) {
        super(id);
        this.name=name;
        this.description=description;
        this.active=active;
        this.envSlug=envSlug;
        this.permissionIds = permissionIds;
    }
}
