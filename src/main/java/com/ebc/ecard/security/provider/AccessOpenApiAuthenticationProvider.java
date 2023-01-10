package com.ebc.ecard.security.provider;

import com.ebc.ecard.application.auth.service.AuthService;
import com.ebc.ecard.domain.auth.OpenApiAccessLogBean;
import com.ebc.ecard.security.authority.Anonymous;
import com.ebc.ecard.security.token.AccessOpenApiAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.Resource;
import java.util.Collections;

public class AccessOpenApiAuthenticationProvider implements AuthenticationProvider {

    private final String HEADER = "X-Consumer-Custom-ID";

    @Resource
    AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccessOpenApiAuthenticationToken token = (AccessOpenApiAuthenticationToken) authentication;

        String customId = token.getCredentials();
        String fpUniqNo = token.getPrincipal();

        try {
            if (StringUtils.isNotEmpty(customId) && StringUtils.isNotEmpty(fpUniqNo)) {
                OpenApiAccessLogBean openApiExistence = authService.getOpenApiExistence(customId);

                if(openApiExistence.isExistenceYn()) {
                    authService.saveAccessLog(new OpenApiAccessLogBean(
                            openApiExistence.getApiName(),
                            fpUniqNo
                    ));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new AccessOpenApiAuthenticationToken(
                customId,
                fpUniqNo,
                Collections.singleton(new Anonymous())
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccessOpenApiAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
