package com.unite.axon_spring.iam.coreapi.command;

import lombok.Data;

@Data
public class UpdateEnvironmentCommand extends BaseCommand<String>{
    private final String slug;
    private final String name;
    private final String description;
    private final String secretKey;
    private final boolean active;
    public UpdateEnvironmentCommand(String id, String slug, String name, String description, String secretKey, boolean active) {
        super(id);
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.secretKey = secretKey;
        this.active = active;
    }
}
