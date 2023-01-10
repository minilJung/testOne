package com.ebc.ecard.security.token;

import com.ebc.ecard.security.authority.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class AccessOpenApiAuthenticationToken extends AbstractAuthenticationToken {

    //private final String userName;
    private final String customId;
    private final String fpUniqNo;

    /**
     * 인증 전 토큰
     */
    public AccessOpenApiAuthenticationToken(String customId, String fpUniqNo) {
        super(Collections.singleton(new User()));
        this.customId = customId;
        this.fpUniqNo = fpUniqNo;

        this.setAuthenticated(false);
    }

    /**
     * 인증 후 토큰
     */
    public AccessOpenApiAuthenticationToken(
            String customId,
            String fpUniqNo,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.customId = customId;
        this.fpUniqNo = fpUniqNo;
        this.setAuthenticated(true);
    }

    @Override
    public String getCredentials() {
        return customId;
    }

    @Override
    public String getPrincipal() {
        return fpUniqNo;
    }
}
