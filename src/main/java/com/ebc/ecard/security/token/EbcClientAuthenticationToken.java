package com.ebc.ecard.security.token;

import com.ebc.ecard.security.authority.Anonymous;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class EbcClientAuthenticationToken extends AbstractAuthenticationToken {

    private final String accountId;

    /**
     * 인증 전 토큰
     */
    public EbcClientAuthenticationToken(String accountId) {
        super(Collections.singleton(new Anonymous()));
        this.accountId = accountId;

        this.setAuthenticated(false);
    }

    /**
     * 인증 후 토큰
     */
    public EbcClientAuthenticationToken(
            String accountId,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);

        this.accountId = accountId;
        this.setAuthenticated(true);
    }

    @Override
    public String getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return accountId;
    }
}
