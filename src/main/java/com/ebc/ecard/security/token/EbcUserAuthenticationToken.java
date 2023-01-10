package com.ebc.ecard.security.token;

import com.ebc.ecard.security.authority.Anonymous;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class EbcUserAuthenticationToken extends AbstractAuthenticationToken {

    private String token;

    private final String userId;

    /**
     * 인증 전 토큰
     */
    public EbcUserAuthenticationToken(String userId, String token) {
        super(Collections.singleton(new Anonymous()));
        this.token = token;
        this.userId = userId;
        this.setAuthenticated(false);
    }

    /**
     * 인증 후 토큰
     */
    public EbcUserAuthenticationToken(
            String userId,
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> detail
    ) {
        super(authorities);
        this.token = null;
        this.userId = userId;
        this.setDetails(detail);
        this.setAuthenticated(true);
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public String getPrincipal() {
        return this.userId;
    }
}
