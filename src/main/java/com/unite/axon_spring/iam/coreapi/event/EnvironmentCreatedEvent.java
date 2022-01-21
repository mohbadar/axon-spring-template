package com.unite.axon_spring.iam.coreapi.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvironmentCreatedEvent extends BaseEvent<String>{

    private final String slug;
    private final String name;
    private final String description;
    private final String secretKey;
    private final boolean active;

    public EnvironmentCreatedEvent(String id, String slug, String name, String description, String secretKey, boolean active) {
        super(id);
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.secretKey = secretKey;
        this.active = active;
    }
}
