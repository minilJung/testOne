package com.ebc.ecard.application.auth.service;

import com.ebc.ecard.domain.auth.AuthBean;
import com.ebc.ecard.domain.auth.AuthenticationBean;
import com.ebc.ecard.domain.auth.OpenApiAccessLogBean;
import com.ebc.ecard.application.auth.dto.AuthenticationDto;
import com.ebc.ecard.security.exception.InvalidRefreshTokenException;
import com.ebc.ecard.security.exception.RefreshTokenExpiredException;
import com.ebc.ecard.util.ReturnMessage;

public interface AuthService {
	ReturnMessage findUserByUserIdPassword(AuthBean bean) throws Exception;
	ReturnMessage saveUserToken(String userId);
	AuthenticationBean findUserAuthenticationByAccessToken(String accessToken);
	AuthenticationDto refreshAccessToken(String refreshToken) throws InvalidRefreshTokenException, RefreshTokenExpiredException;
	ReturnMessage updateUserAuthenticationToExpire(AuthenticationBean bean);
	OpenApiAccessLogBean getOpenApiExistence(String customId);
	ReturnMessage saveAccessLog(OpenApiAccessLogBean bean) throws Exception;
}