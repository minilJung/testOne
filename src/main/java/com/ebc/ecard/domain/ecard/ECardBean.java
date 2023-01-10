package com.ebc.ecard.domain.ecard;

import com.ebc.ecard.application.contract.dto.ContractInfoDto;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardBean {
	private String ecardId;             // 전자명함 아이디
	private String companyId;      	    // 회사 아이디
	private String userId;              // 사용자 아이디
	private String employeeNo;          // 사번
	private String position;			// 직급
	private String fpId;           		// fp 아이디
	private String name;				// fp 이름
	private String department;          // 부서
	private String email;          		// 사내 이메일
	private String branchNumber;        // 지점 번호
	private String faxNumber;           // 팩스 번호
	private String profileImageUrl;       // 프로필 이미지 주소
	private String profileFileId;       // 프로필 파일 uuid

	private String previewImageUrl;       // 이미지 저장 시 제공 파일 주소
	private String previewFileId;       // 이미지 저장 시 제공 파일 uuid

	private String ecardChangedYn;       // 마지막 생성 이후 이미지 저장 시 제공 파일 수정 여부
	private String profileTemplateNo;			// 전자명함 배경 이미지 번호
	private String lastUpdatedAt;       // 마지막 업데이트 일시
	private int sharedCount;         // 공유 횟수
	private Instant lastSharedAt;        // 마지막 공유 일시
	private int viewCount;           // 조회수
	private Instant lastViewedAt;        // 마지막 조회 일시
	private String customersPublicYn;   // 고객 수 공개여부
	private int customerCounts;   // 고객 수
	private String contractsPublicYn;   // 계약 수 공개여부
	private int contractCounts;   // 계약 수
	private String contractRatePublicYn;   // 계약유지율 공개여부
	private float contractRate;   // 계약유지율
	private String imperfectRatePublicYn;   // 불완전판매율 공개여부
	private int imperfectCounts;   // 불완전판매율 공개여부
	private String branchNumberPublicYn;   // 불완전판매율 공개여부
	private String faxNumberPublicYn;   // 불완전판매율 공개여부
	private String badgePublicYn;   // 불완전판매율 공개여부
	private String deleteYn;			// 삭제여부
	private String onDuty;				// 재직여부

	private String profileFileName;
	private String previewFileName;

	public ECardBean(String ecardId, String companyId, String userId, String fpId) {
		this.ecardId = ecardId;
		this.companyId = companyId;
		this.userId = userId;
		this.fpId = fpId;

		this.customersPublicYn = "Y";
		this.contractsPublicYn = "Y";
		this.contractRatePublicYn = "Y";
		this.imperfectRatePublicYn = "Y";
		this.branchNumberPublicYn = "Y";
		this.faxNumberPublicYn = "Y";
		this.badgePublicYn = "Y";
	}

	public ECardBean(
		String ecardId,
		String companyId,
		String userId,
		String email,
		String fpId,
		String position,
		String branchName
	) {
		this.ecardId = ecardId;
		this.companyId = companyId;
		this.userId = userId;
		this.email = email;
		this.fpId = fpId;
		this.position = position;
		this.department = branchName;

		this.customersPublicYn = "Y";
		this.contractsPublicYn = "Y";
		this.contractRatePublicYn = "Y";
		this.imperfectRatePublicYn = "Y";
		this.branchNumberPublicYn = "Y";
		this.faxNumberPublicYn = "Y";
		this.badgePublicYn = "Y";
	}

	public void setEmployeeCvInfo(
		String email,
		String department,
		String onDuty,
		String position
	) {
		this.email = email;
		this.department = department;
		this.onDuty = onDuty;
		this.position = position;
	}

	public void setContractInfo(ContractInfoDto contractInfo) {
		this.customerCounts = contractInfo.getCustCnt();
		this.contractCounts = contractInfo.getCntcCnt();
		this.contractRate = contractInfo.getCntcMaintainRate();
		this.imperfectCounts = contractInfo.getImperfectCntcCnt();
	}

	public void visit() {
		this.viewCount = this.viewCount + 1;
		this.lastViewedAt = Instant.now();
	}

	public void share() {
		this.sharedCount = this.sharedCount + 1;
		this.lastSharedAt = Instant.now();
	}
}