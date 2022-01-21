package com.unite.axon_spring.iam.coreapi.event;

public class GroupDeactivatedEvent extends BaseEvent<String>{
    public GroupDeactivatedEvent(String id) {
        super(id);
    }
}
