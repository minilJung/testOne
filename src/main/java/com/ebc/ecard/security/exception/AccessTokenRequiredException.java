package com.ebc.ecard.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AccessTokenRequiredException extends AuthenticationException {

    public AccessTokenRequiredException() {
        super("Access token is required");
    }
}
