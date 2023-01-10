package com.ebc.ecard.security.filter;

import com.ebc.ecard.security.ECardRequestMatcher;
import com.ebc.ecard.security.OpenApiRequestMatcher;
import com.ebc.ecard.security.token.AccessOpenApiAuthenticationToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessOpenApiAuthenticationFilter extends AbstractECardAuthenticationFilter {
    public AccessOpenApiAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint
    ) {
        super(
            authenticationManager,
            new OpenApiRequestMatcher(),
            authenticationEntryPoint
        );
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        Map<String, String> headers = getUserNameAndCustomIdFromRequest(request);

        if (headers != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String parameters = null;
            try {
                parameters = objectMapper.writeValueAsString(request.getParameterMap());
            } catch(Exception e) {
                // ignore
            }
            log.info("open api access log {} {}, query {}, params {}", request.getMethod(), request.getRequestURI(), request.getQueryString(), parameters);
            log.info("Request received from {}", headers.get("CUSTOM_ID"));
            Authentication authentication = new AccessOpenApiAuthenticationToken(
                    headers.get("CUSTOM_ID"),
                    headers.get("fpUniqNo")
            );

            Authentication result = getAuthenticationManager().authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return result;
        }
        log.info("Header is null");

        return null;
    }

    private Map<String, String> getUserNameAndCustomIdFromRequest(HttpServletRequest request) {
        String customId = request.getHeader("X-Consumer-Custom-ID");
        String fpUniqNo = request.getParameter("fpUniqNo");

        if (StringUtils.isNotEmpty(customId)) {
            Map<String, String> headers = new HashMap<>();
            headers.put("CUSTOM_ID", customId);
            headers.put("fpUniqNo", fpUniqNo);

            return headers;
        }

        return null;
    }
}
