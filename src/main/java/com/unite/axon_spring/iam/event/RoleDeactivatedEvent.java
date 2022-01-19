package com.unite.axon_spring.iam.event;

public class RoleDeactivatedEvent extends BaseEvent<String>{
    public RoleDeactivatedEvent(String id) {
        super(id);
    }
}
