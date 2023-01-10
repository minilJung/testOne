package com.ebc.ecard.security.filter;

import com.ebc.ecard.security.ECardRequestMatcher;
import com.ebc.ecard.security.token.AccessTokenAuthenticationToken;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessTokenAuthenticationFilter extends AbstractECardAuthenticationFilter {

    private final String BEARER_PREFIX = "bearer ";
    private final String COOKIE_VARIABLE = "AUTH_SESSION_ID";
    private final Logger logger = LoggerFactory.getLogger(AccessTokenAuthenticationFilter.class);

    public AccessTokenAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint
    ) {
        super(authenticationManager, new ECardRequestMatcher(), authenticationEntryPoint);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String accessToken = getJwtFromRequest(request);
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }

        Authentication authentication = new AccessTokenAuthenticationToken(accessToken);

        Authentication result = getAuthenticationManager().authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return result;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.toLowerCase().startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        return null;
    }

}
