package com.unite.axon_spring.iam.query;

import lombok.Data;

@Data
public class GetUserByIdQuery {
    private final String id;
}