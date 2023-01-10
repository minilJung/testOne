package com.ebc.ecard.mapper;

import com.ebc.ecard.domain.auth.AuthBean;
import com.ebc.ecard.domain.auth.AuthenticationBean;
import com.ebc.ecard.domain.auth.OpenApiAccessLogBean;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AuthMapper {
	AuthBean userExistenceByUserIdPassword(AuthBean bean);
	Integer createAuthenticationInfo(AuthenticationBean auth);

	AuthenticationBean findAuthenticationInfoByAccessToken(String accessToken);

	Integer updateAuthenticationInfo(AuthenticationBean authenticationBean);

	Integer updateAuthenticationInfoToExpire(String authenticationId);

	AuthenticationBean findAuthenticationInfoByRefreshToken(String refreshToken);

	Map<String, Object> getOpenApiExistence(String customId);

	Integer saveAccessLog(OpenApiAccessLogBean bean);
}