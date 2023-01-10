package com.ebc.ecard.security.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidAccessTokenException extends AuthenticationException {

    public InvalidAccessTokenException() {
        super("Invalid access token");
    }
}
