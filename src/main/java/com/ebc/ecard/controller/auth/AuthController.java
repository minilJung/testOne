package com.ebc.ecard.controller.auth;

import com.ebc.ecard.domain.auth.AuthBean;
import com.ebc.ecard.application.auth.service.AuthService;
import com.ebc.ecard.domain.auth.AuthenticationBean;
import com.ebc.ecard.domain.user.UserBean;
import com.ebc.ecard.application.auth.dto.FpLoginDto;
import com.ebc.ecard.application.user.service.UserService;
import com.ebc.ecard.security.exception.InvalidRefreshTokenException;
import com.ebc.ecard.security.exception.RefreshTokenExpiredException;
import com.ebc.ecard.util.ReturnMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Resource
	AuthService service;

	@Resource
	UserService userService;

	@Value("${ecard.session.variable}")
	String SESSION_VARIABLE;
	
	@Value("${ecard.cookie.variable}")
	String COOKIE_VARIABLE;
	
	/**
	 * @title	- 사용자 로그인
	 * @desc	- 아이디 / 비밀번호로 로그인 한다.
	 * @author	- Jinyeon
	 * @date	- 2022.06.30
	 * @param	- AuthBean : 인증용 VO
	 * @return	- 결과 코드
	 */
	@PostMapping("/login")
	public ReturnMessage apiAuthLogin(@RequestBody AuthBean bean) {
		try {
			ReturnMessage rm = service.findUserByUserIdPassword(bean);

			if(rm.getResult().equals("0000")) {
				AuthBean result = (AuthBean) rm.getValue();
				return service.saveUserToken(result.getUserId());
			} else {
				return rm;
			}
		} catch(Exception e) {
			return new ReturnMessage("9999", "로그인 API 호출 에러", e);
		}
	}

	/**
	 * @title    - fpUniqNo로 로그인
	 * @desc    - fpUniqNo로 로그인 요청
	 * @author    - Jinyeon
	 * @date    - 2021.12.20
	 * @param    - UserBean
	 * @return    - 결과 코드 (9001: 중복 아이디)
	 */
	@PostMapping("/fp/login")
	public ReturnMessage getUserPersonalInfoByFpUniqNo(
		@RequestBody FpLoginDto fpLoginDto
	) {
		try {
			UserBean user = userService.getUserInfoByFpId(fpLoginDto.getFpUniqNo());
			if (user == null) {
				return new ReturnMessage("9999", "FP 로그인 API 에러", new RuntimeException("등록되지 않은 fp입니다."));
			}

			return service.saveUserToken(user.getUserId());
		} catch (Exception e) {
			return new ReturnMessage("9999", "FP 로그인 API 에러", e);
		}
	}

	/**
	 * @title	- 사용자 토큰 갱신
	 * @desc	- Refresh Token을 이용해 토큰을 갱신한다
	 * @author	- Jgpark
	 * @date	- 2022.06.28
	 * @param	- AuthenticationBean : 인증용 VO
	 * @return	- 결과 코드
	 */
	@PostMapping("/refresh")
	public ReturnMessage apiAuthRefresh(@RequestBody AuthenticationBean authBean, HttpServletRequest request, HttpServletResponse response) {
		try {
			return new ReturnMessage(service.refreshAccessToken(authBean.getRefreshToken()));
		} catch(InvalidRefreshTokenException e) {
			log.info("Refresh token is invalid. {}", authBean.getRefreshToken());
			return new ReturnMessage(
				"401",
				"Invalid Refresh Token",
				e
			);
		} catch(RefreshTokenExpiredException e) {
			log.info("Refresh token is expired. expiredAt, {}", authBean.getRefreshTokenExpiresAt());
			return new ReturnMessage(
				"401",
				"Refresh Token Expired",
				e
			);
		} catch(Exception e) {
			return new ReturnMessage("9999", "로그인 API 호출 에러", e);
		}
	}
	
	/**
	 * @title	- 사용자 로그아웃
	 * @desc	- 사용자를 로그아웃 시킨다.
	 * @author	- Jinyeon
	 * @date	- 2021.12.20
	 * @param	- HttpServletRequest 
	 * @return	- 결과 코드
	 */
	@GetMapping("/logout")
	public ReturnMessage apiAuthLogout(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			if(session != null) session.invalidate();
			
			Cookie c = new Cookie(COOKIE_VARIABLE, null);
			c.setMaxAge(0);
			c.setPath("/");
			
			response.addCookie(c);
			
			return new ReturnMessage();
		} catch(Exception e) {
			return new ReturnMessage("9999", "로그아웃 API 호출 에러", e);
		}
	}

}
