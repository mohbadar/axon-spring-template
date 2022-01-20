package com.unite.axon_spring.iam.config;

public class JwtTokenConstants {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "McIt@#786*";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}