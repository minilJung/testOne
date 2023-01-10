package com.ebc.ecard.security.filter;

import com.ebc.ecard.security.ECardRequestMatcher;
import com.ebc.ecard.security.authority.Anonymous;
import com.ebc.ecard.security.token.EbcUserAuthenticationToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EbcAnonymousAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger logger = LoggerFactory.getLogger(EbcAnonymousAuthenticationFilter.class);

    public EbcAnonymousAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint
    ) {
        super(new ECardRequestMatcher());

        setAuthenticationManager(authenticationManager);
        setAuthenticationFailureHandler(
                new AuthenticationEntryPointFailureHandler(authenticationEntryPoint)
        );
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Authentication authenticationResult = attemptAuthentication(request, response);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);

            chain.doFilter(request, response);
        } catch (InternalAuthenticationServiceException failed) {
            this.logger.error("An internal error occurred while trying to authenticate the user.", failed);
            unsuccessfulAuthentication(request, response, failed);
        } catch (AuthenticationException ex) {
            // Authentication failed
            unsuccessfulAuthentication(request, response, ex);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        return new EbcUserAuthenticationToken(
                "ANONYMOUS",
                Collections.singleton(new Anonymous()),
                null
        );
    }

}
