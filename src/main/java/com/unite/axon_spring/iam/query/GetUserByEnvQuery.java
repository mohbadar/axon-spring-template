package com.unite.axon_spring.iam.query;

import lombok.Data;

@Data
public class GetUserByEnvQuery {
    private final String envSlug;
}
