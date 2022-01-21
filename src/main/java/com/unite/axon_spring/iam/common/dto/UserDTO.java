package com.unite.axon_spring.iam.common.dto;

import com.unite.axon_spring.iam.common.validation.UniqueEmail;
import com.unite.axon_spring.iam.common.validation.UniquePhoneNo;
import com.unite.axon_spring.iam.common.validation.UniqueUsername;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ToString
public class UserDTO {
    private String id;
    @NotNull
    @Size(min=3, message="Name should have at least 3 characters")
    private String name;
    private String address;
    private boolean active;
    @NotNull
    @Size(min=10, message="Phone should have at least 10 characters")
    @UniquePhoneNo
    private String phoneNo;
    @NotNull
    @Size(min=3, message="Username should have at least 3 characters")
    @UniqueUsername
    private String username;
    @NotNull
    @Size(min=8, message="Password should have at least 8 characters")
    private String password;
    @Email
    @UniqueEmail
    private String email;
    private List<String> environmentIds;
    private List<String> groupIds;
}
