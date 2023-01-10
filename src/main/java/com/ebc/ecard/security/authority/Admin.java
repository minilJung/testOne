package com.ebc.ecard.security.authority;

import org.springframework.security.core.GrantedAuthority;

public class Admin implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ROLE_ADMIN";
    }
}
