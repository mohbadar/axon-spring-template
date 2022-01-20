package com.unite.axon_spring.iam.event;

import lombok.Data;

import java.util.List;

@Data
public class GroupCreatedEvent extends BaseEvent<String>{
    private final String name;
    private final String description;
    private final boolean active;
    private final String envSlug;
    private final List<String> roleIds;

    public GroupCreatedEvent(String id,String name, String description, boolean active, String envSlug, List<String> roleIds) {
        super(id);
        this.name = name;
        this.description = description;
        this.active = active;
        this.envSlug = envSlug;
        this.roleIds = roleIds;
    }
}