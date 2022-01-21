package com.unite.axon_spring.iam.coreapi.command;

import lombok.Data;

import java.util.List;

@Data
public class CreateGroupCommand extends BaseCommand<String>{
    private final String name;
    private final String description;
    private final boolean active;
    private final String envSlug;
    private final List<String> roleIds;

    public CreateGroupCommand(String id, String name, String description, boolean active, String envSlug, List<String> roleIds) {
        super(id);
        this.name = name;
        this.description = description;
        this.active = active;
        this.envSlug = envSlug;
        this.roleIds = roleIds;
    }
}
