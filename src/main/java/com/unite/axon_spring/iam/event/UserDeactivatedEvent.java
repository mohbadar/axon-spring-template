package com.unite.axon_spring.iam.event;

public class UserDeactivatedEvent extends BaseEvent<String>{
    public UserDeactivatedEvent(String id) {
        super(id);
    }
}
