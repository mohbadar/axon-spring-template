package com.unite.axon_spring.iam.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserFullViewDTO {
    private String id;
    private String name;
    private String address;
    private boolean active;
    private String phoneNo;
    private String username;
    private String password;
    private String email;
    private List<String> environments;
    private List<String> groups;
}
