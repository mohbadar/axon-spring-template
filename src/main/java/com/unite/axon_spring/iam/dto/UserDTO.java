package com.unite.axon_spring.iam.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserDTO {
    private String id;
    private String name;
    private String address;
    private boolean active;
    private String phoneNo;
    private String username;
    private String password;
    private String email;
    private List<String> environmentIds;
    private List<String> groupIds;
}
