package com.ebc.ecard.application.auth.dto;

import com.ebc.ecard.domain.auth.AuthenticationBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 기존 AuthBean과 duplicated naming을 방지하기위해 AuthenticationBean으로 명명
 */
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationDto {
    private String authenticationId;
    private String userId;
    private String accessToken;
    private String refreshToken;
    private String accessTokenExpiresAt;
    private String refreshTokenExpiresAt;
    private String expiredYn;
    private String createdAt;

    public static AuthenticationDto of(AuthenticationBean bean) {
        return new AuthenticationDto(
            bean.getAuthenticationId(),
            bean.getUserId(),
            bean.getAccessToken(),
            bean.getRefreshToken(),
            bean.getAccessTokenExpiresAt().toString(),
            bean.getRefreshTokenExpiresAt().toString(),
            bean.getExpiredYn(),
            bean.getCreatedAt().toString()
        );
    }

    public AuthenticationDto(
        String authenticationId,
        String userId,
        String accessToken,
        String refreshToken,
        String accessTokenExpiresAt,
        String refreshTokenExpiresAt,
        String expiredYn,
        String createdAt
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