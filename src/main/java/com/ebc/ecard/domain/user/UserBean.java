package com.ebc.ecard.domain.user;

import com.ebc.ecard.application.contract.dto.ContractInfoDto;
import com.ebc.ecard.application.contract.dto.UserContractInfoDto;
import com.ebc.ecard.domain.agreement.UserAgreementBean;
import com.ebc.ecard.domain.badge.UserBadgeBean;
import com.ebc.ecard.domain.career.UserCareerBean;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.domain.insurance.UserInsuranceBean;
import com.ebc.ecard.domain.majority.UserMajorityBean;
import com.ebc.ecard.domain.qualification.UserQualificationBean;
import com.ebc.ecard.domain.qualification.UserRegistrationBean;
import com.ebc.ecard.util.BaseBean;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserBean {
	private String ecardId;
	private String companyId;		// 회사아이디
	private String employeeNo;		// 사번
	private String fpId;		// 사번
	private String userId;			// 사용자 아이디
	private String password;		// 비밀번호
	private String accountId;		// 통합 아이디
	private String newPassword;		// 신규비밀번호
	private String name;			// 이름
	private String di;			// 고유번호
	private String ci;			// 고유번호
	private String mobileNo;		// 모바일 전화번호
	private Date birthdate;		// 생년월일
	private String faxNo;			// 팩스번호
	private String email;			// 이메일
	private String kakaotalkId;		// 카카오톡 아이디
	private Instant createdAt; // 가입일시
	private String lastUpdatedAt;	// 마지막 업데이트 일시
	private String lastLoginAt;		// 마지막 로그인 일시
	private String sessionId;		// 세션 아이디
	private String deleteYn;		// 삭제여부

	private List<UserAgreementBean> agreementList;
	private List<UserCareerBean> careerList;
	private List<UserQualificationBean> qualificationList;
	private List<UserBadgeBean> badgeList;
	private List<UserInsuranceBean> insuranceList;
	private List<UserMajorityBean> majorityList;
	private UserRegistrationBean registration;
	private ECardBean ecard;

	public UserBean(
		String userId,
		String password,
		String accountId,
		String name,
		String di,
		String ci,
		String mobileNo,
		Date birthdate,
		String email,
		String deleteYn
	) {
		this.userId = userId;
		this.password = password;
		this.accountId = accountId;
		this.name = name;
		this.di = di;
		this.ci = ci;
		this.mobileNo = mobileNo;
		this.birthdate = birthdate;
		this.email = email;
		this.deleteYn = deleteYn;
	}

}