package com.ebc.ecard.security.provider;

import com.ebc.ecard.application.auth.service.AuthService;
import com.ebc.ecard.domain.auth.AuthenticationBean;
import com.ebc.ecard.application.user.service.UserService;
import com.ebc.ecard.security.authority.User;
import com.ebc.ecard.security.exception.AccessTokenExpiredException;
import com.ebc.ecard.security.exception.InvalidAccessTokenException;
import com.ebc.ecard.security.token.AccessTokenAuthenticationToken;
import com.ebc.ecard.security.token.EbcUserAuthenticationToken;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Resource;

public class EbcUserAuthenticationProvider implements AuthenticationProvider {

    @Resource
    UserService userService;

    @Resource
    AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Instant now = Instant.now();
        AccessTokenAuthenticationToken token = (AccessTokenAuthenticationToken) authentication;

        String accessToken = token.getPrincipal();

        AuthenticationBean authBean = authService.findUserAuthenticationByAccessToken(accessToken);
        if (authBean == null) {
            throw new InvalidAccessTokenException();
        }
        if (!now.isBefore(authBean.getAccessTokenExpiresAt())) {
            throw new AccessTokenExpiredException();
        }

        Map<String, Object> result = userService.findUserByUserId(authBean.getUserId());
        if (result == null) {
            throw new InvalidAccessTokenException();
        }

        return new EbcUserAuthenticationToken(
            authBean.getUserId(),
            Collections.singleton(new User()),
            result
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccessTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
