package com.ebc.ecard.domain.auth;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationBean {
    private String authenticationId;
    private String userId;
    private String accessToken;
    private String refreshToken;
    private Instant accessTokenExpiresAt;
    private Instant refreshTokenExpiresAt;
    private String expiredYn;
    private Instant createdAt;

    public static AuthenticationBean create(
        String authenticationId,
        String userId,
        String accessToken,
        String refreshToken,
        Instant accessTokenExpiresAt,
        Instant refreshTokenExpiresAt
    ) {
        return new AuthenticationBean(
            authenticationId,
            userId,
            accessToken,
            refreshToken,
            accessTokenExpiresAt,
            refreshTokenExpiresAt,
            "N",
            Instant.now()
        );
    }

    public AuthenticationBean(
        String authenticationId,
        String userId,
        String accessToken,
        String refreshToken,
        Instant accessTokenExpiresAt,
        Instant refreshTokenExpiresAt,
        String expiredYn,
        Instant createdAt
    ) {
        this.authenticationId = authenticationId;
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
        this.expiredYn = expiredYn;
        this.createdAt = createdAt;
    }
}