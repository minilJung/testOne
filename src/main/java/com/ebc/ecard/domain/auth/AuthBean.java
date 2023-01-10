package com.ebc.ecard.domain.auth;

import com.ebc.ecard.util.BaseBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthBean extends BaseBean {
	private String companyId;		// 회사아이디
	private String employeeNo;		// 사번

	private String userId;			// 사용자 아이디
	private String ecardId;			// 전자명함 아이디

	private String password;		// 비밀번호
	private String name;			// 이름
	private String mobileNo;		// 모바일 전화번호
	private String email;			// 이메일
	private String kakaotalkId;		// 카카오톡 아이디
	private String sessionId;		// 세션 아이디
	private String lastLoginAt;		// 마지막 로그인 일시
	private String lastUpdatedAt;	// 마지막 업데이트 일시
	
	private boolean autoLogin;		// 자동로그인
}