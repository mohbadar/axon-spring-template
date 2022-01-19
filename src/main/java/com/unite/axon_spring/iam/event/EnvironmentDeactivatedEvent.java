package com.unite.axon_spring.iam.event;

public class EnvironmentDeactivatedEvent extends BaseEvent<String> {
    public EnvironmentDeactivatedEvent(String id) {
        super(id);
    }
}
