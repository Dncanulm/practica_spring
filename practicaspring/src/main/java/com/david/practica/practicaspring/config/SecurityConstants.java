package com.david.practica.practicaspring.config;

public class SecurityConstants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 60L * 60L; //one hour
    public static final long REFRESH_TOKEN_VALIDITY_SECONDS = 365L * 24L * 60L * 60L; //15 days
    public static final String SIGNING_KEY = "linFIhlB1eaLiOU98w2AChIgUrtldBZ1kGmzW0BrScE8SvfYurXcZEuZ2hY6G9Nk";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "permissions";

    private SecurityConstants() {
        throw new IllegalStateException("Utility Class");
    }
}

