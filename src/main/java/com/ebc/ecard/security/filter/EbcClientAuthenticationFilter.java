package com.ebc.ecard.security.filter;

import com.ebc.ecard.security.ECardRequestMatcher;
import com.ebc.ecard.security.token.EbcClientAuthenticationToken;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EbcClientAuthenticationFilter extends AbstractECardAuthenticationFilter {

    public EbcClientAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint
    ) {
        super(authenticationManager, new ECardRequestMatcher(), authenticationEntryPoint);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String accountId = getClientAccountIdFromRequest(request);
        if(StringUtils.isEmpty(accountId)) {
            return null;
        }

        Authentication authentication = new EbcClientAuthenticationToken(accountId);

        Authentication result = getAuthenticationManager().authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(result);

        return result;
    }

    private String getClientAccountIdFromRequest(HttpServletRequest request) {
        String headers = request.getHeader("x-iam-service-id");

        String accountId = "";
        //String accountId = "오주형_9Zp7aFiXVmLmXU1ufRs52QcAb+zZvqGetNS5cinWl/u4RmechaVOYepExLIyS5q8O5zZWfeOc1pRk4QBGyJQvQ==";
        if (headers == null) {
            accountId = headers;
            return accountId;
        }

        return null;
    }
}
