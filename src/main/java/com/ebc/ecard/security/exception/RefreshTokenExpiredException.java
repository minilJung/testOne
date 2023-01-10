package com.ebc.ecard.security.exception;

import org.springframework.security.core.AuthenticationException;

public class RefreshTokenExpiredException extends AuthenticationException {

    public RefreshTokenExpiredException() {
        super("Refresh token has expired.");
    }
}
