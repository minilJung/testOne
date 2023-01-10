package com.ebc.ecard.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class OpenApiRequestMatcher implements RequestMatcher{

    @Override
    public boolean matches(HttpServletRequest request) {
        // OPEN API
        if (request.getRequestURI().contains("/api/users/existence")) {
            return true;
        }
        return false;
    }
}
