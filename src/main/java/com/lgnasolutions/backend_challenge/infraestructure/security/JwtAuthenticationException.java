package com.lgnasolutions.backend_challenge.infraestructure.security;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
