package com.ebc.ecard.application.auth.service;

import com.ebc.ecard.domain.auth.AuthBean;
import com.ebc.ecard.mapper.AuthMapper;
import com.ebc.ecard.domain.auth.AuthenticationBean;
import com.ebc.ecard.domain.auth.OpenApiAccessLogBean;
import com.ebc.ecard.application.auth.dto.AccessTokenPayloadDto;
import com.ebc.ecard.application.auth.dto.AuthenticationDto;
import com.ebc.ecard.mapper.UserMapper;
import com.ebc.ecard.security.exception.InvalidRefreshTokenException;
import com.ebc.ecard.security.exception.RefreshTokenExpiredException;
import com.ebc.ecard.util.AES256;
import com.ebc.ecard.util.JwtUtil;
import com.ebc.ecard.util.ReturnMessage;
import com.ebc.ecard.util.XeCommon;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lombok.NonNull;

@Service
public class AuthServiceImpl implements AuthService {

    private final int ACCESS_TOKEN_EXPIRES_IN = (60 * 30 * 1000);
    private final int REFRESH_TOKEN_EXPIRES_IN = (60 * 60 * 24 * 14 * 1000);

    @Resource
    AuthMapper mapper;

    @Resource
    UserMapper userMapper;

    @Resource
    XeCommon common;

    @Override
    public ReturnMessage findUserByUserIdPassword(AuthBean bean) throws Exception {
        AuthBean loginResult = mapper.userExistenceByUserIdPassword(bean);
        //AuthBean loginResult = mapper.findUserByUserIdPassword(bean);

        if (loginResult == null) {
            return new ReturnMessage("9901", "정보에 해당하는 사용자가 존재하지 않습니다", "실패");
        } else {
//			System.out.println(loginResult.toStringMultiLine());
            return new ReturnMessage(loginResult);
        }
    }

    @Override
    @Transactional
    public ReturnMessage saveUserToken(String userId) {
        Date now = new Date();

        AccessTokenPayloadDto payload = new AccessTokenPayloadDto(userId);
        String token = JwtUtil.generateUserToken(payload);
        Date tokenExpiresAt = JwtUtil.getTokenExpiration(token);

        String authenticationId = common.getUuid(false);
        String refreshToken = common.getRandomString(false, 128);

        AuthenticationBean authenticationBean = AuthenticationBean.create(
                authenticationId,
                userId,
                token,
                refreshToken,
                Instant.ofEpochMilli(tokenExpiresAt.getTime()),
                Instant.ofEpochMilli(now.getTime() + REFRESH_TOKEN_EXPIRES_IN)
        );

        Integer resultCount = mapper.createAuthenticationInfo(authenticationBean);
        userMapper.updateUserLastLoginAtByUserId(authenticationBean.getUserId());
        if (resultCount > 0) {
            return new ReturnMessage(AuthenticationDto.of(authenticationBean));
        }

        return new ReturnMessage(false);
    }

    @Override
    public AuthenticationBean findUserAuthenticationByAccessToken(String accessToken) {
        if (accessToken == null) {
            return null;
        }

        return mapper.findAuthenticationInfoByAccessToken(accessToken);
    }

    @Override
    public AuthenticationDto refreshAccessToken(@NonNull String refreshToken) throws InvalidRefreshTokenException, RefreshTokenExpiredException {

        AuthenticationBean bean = mapper.findAuthenticationInfoByRefreshToken(refreshToken);
        if (bean == null) {
            throw new InvalidRefreshTokenException();
        }

        HashMap<String, Object> userMap = userMapper.findUserMapByUserId(bean.getUserId());
        if(userMap == null) {
            throw new InvalidRefreshTokenException();
        }

        Instant now = Instant.now();
        if (bean.getExpiredYn().equals("Y") || !now.isBefore(bean.getRefreshTokenExpiresAt())) {
            mapper.updateAuthenticationInfoToExpire(bean.getAuthenticationId());
            throw new RefreshTokenExpiredException();
        }

        AccessTokenPayloadDto payload = new AccessTokenPayloadDto();
        payload.setUserId(bean.getUserId());
        String token = JwtUtil.generateUserToken(payload);

        bean.setAccessToken(token);
        bean.setAccessTokenExpiresAt(now.plus(ACCESS_TOKEN_EXPIRES_IN, ChronoUnit.MILLIS));
        mapper.updateAuthenticationInfo(bean);

        return AuthenticationDto.of(bean);
    }

    @Override
    public ReturnMessage updateUserAuthenticationToExpire(AuthenticationBean bean) {

        if (mapper.updateAuthenticationInfoToExpire(bean.getAuthenticationId()) > 0) {
            return new ReturnMessage(false);
        }

        return new ReturnMessage(true);
    }

    @Override
    public OpenApiAccessLogBean getOpenApiExistence(String customId) {
        Map<String, Object> existenceResult = mapper.getOpenApiExistence(customId);
        boolean existenceYn = existenceResult.get("existenceYn").equals("Y") ? true : false;

        return new OpenApiAccessLogBean(
                (String) existenceResult.get("apiName"),
                existenceYn
        );
    }

    @Override
    public ReturnMessage saveAccessLog(OpenApiAccessLogBean bean) throws Exception {
        String fpUniqNo = AES256.decrypt(bean.getFpUniqNo(), AES256.EBC_AES256_KEY);
        bean.setFpUniqNo(fpUniqNo);

        return new ReturnMessage(mapper.saveAccessLog(bean));
    }
}
