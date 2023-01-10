package com.ebc.ecard.security.provider;

import com.ebc.ecard.security.token.EbcClientAuthenticationToken;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.Resource;

public class EbcClientAuthenticationProvider implements AuthenticationProvider {

    //private final String DEV_REQUEST_URL = "https://dev.iam.ent-bc.com";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EbcClientAuthenticationToken token = (EbcClientAuthenticationToken) authentication;

        String accountId = token.getPrincipal();

//        boolean result = iamService.findUserByAccountId(accountId);
//        if (result == false ) {
//            throw new InvalidAccountIdException();
//        }

        return new EbcClientAuthenticationToken(accountId);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EbcClientAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
