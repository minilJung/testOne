package com.ebc.ecard.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AccessTokenNotFoundException extends AuthenticationException {

    public AccessTokenNotFoundException() {
        super("Access token not found.");
    }
}
