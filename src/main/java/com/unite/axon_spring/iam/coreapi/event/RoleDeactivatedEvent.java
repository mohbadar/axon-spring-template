package com.unite.axon_spring.iam.coreapi.event;

public class RoleDeactivatedEvent extends BaseEvent<String>{
    public RoleDeactivatedEvent(String id) {
        super(id);
    }
}
