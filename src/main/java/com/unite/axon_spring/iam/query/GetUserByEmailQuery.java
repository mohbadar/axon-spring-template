package com.unite.axon_spring.iam.query;

import lombok.Data;

@Data
public class GetUserByEmailQuery {
    private final String email;
}
