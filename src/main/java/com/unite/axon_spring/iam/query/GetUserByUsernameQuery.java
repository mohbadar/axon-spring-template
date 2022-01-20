package com.unite.axon_spring.iam.query;

import lombok.Data;

@Data
public class GetUserByUsernameQuery {
    private final String username;
}
