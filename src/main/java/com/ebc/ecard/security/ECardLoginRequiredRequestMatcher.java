package com.ebc.ecard.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class ECardLoginRequiredRequestMatcher implements RequestMatcher {

    @Override
    public boolean matches(HttpServletRequest request) {
        // ECARD 관련
        String uri = request.getRequestURI();
        if (uri.contains("/cv/")) {

            if (!uri.endsWith("/profile")
                && !uri.endsWith("/profile-backgrounds")
                && !uri.endsWith("/insurances")
                && !uri.endsWith("/contract-info")
                && !uri.endsWith("/vcard-file")
                && !uri.endsWith("/nft-profile")
                && !uri.endsWith("/kakaotalk-keys")
                && !uri.endsWith("image")
                && !uri.endsWith("metadata")
                && !uri.endsWith("existence")
                && !uri.endsWith("account-id")
                && !uri.endsWith("registrations")
                && !uri.matches(".*./customer-link.*")
                && !uri.matches(".*./.*.-count")
                && !uri.matches(".*./public.*")
            ) {
                return true;
            }

        }

        if (request.getRequestURI().contains("/me")) {
            return true;
        }

        return false;
    }
}
