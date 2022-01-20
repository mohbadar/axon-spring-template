package com.unite.axon_spring.iam.event;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserCreatedEvent extends BaseEvent<String>{
    private final String name;
    private final String address;
    private final boolean active;
    private final String phoneNo;
    private final String username;
    private final String password;
    private final String email;
    private final List<String> environmentIds;
    private final List<String> groupIds;

    public UserCreatedEvent(String id, String name, String address, boolean active, String phoneNo, String username, String password, String email, List<String> environmentIds, List<String> groupIds) {
        super(id);
        this.name = name;
        this.address = address;
        this.active = active;
        this.phoneNo = phoneNo;
        this.username = username;
        this.password = password;
        this.email = email;
        this.environmentIds = environmentIds;
        this.groupIds = groupIds;
    }
}