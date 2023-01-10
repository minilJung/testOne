package com.ebc.ecard.security.token;

import com.ebc.ecard.security.authority.Anonymous;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class AccessTokenAuthenticationToken extends AbstractAuthenticationToken {

    private String accessToken;

    /**
     * 인증 전 토큰
     */
    public AccessTokenAuthenticationToken(String accessToken) {
        super(Collections.singleton(new Anonymous()));
        this.accessToken = accessToken;
        this.setAuthenticated(false);
    }

    /**
     * 인증 후 토큰
     */
    public AccessTokenAuthenticationToken(
            String accessToken,
            Map<String, Object> detail,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.accessToken = accessToken;
        this.setDetails(detail);
        this.setAuthenticated(true);
    }

    @Override
    public String getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return accessToken;
    }
}
