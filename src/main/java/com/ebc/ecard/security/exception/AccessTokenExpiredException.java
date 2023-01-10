package com.ebc.ecard.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AccessTokenExpiredException extends AuthenticationException {

    public AccessTokenExpiredException() {
        super("Access token has expired.");
    }
}
