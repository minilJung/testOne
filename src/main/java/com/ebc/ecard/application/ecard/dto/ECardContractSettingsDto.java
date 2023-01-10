package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.util.BaseBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardContractSettingsDto extends BaseBean {
	private String ecardId;             // 전자명함 아이디
	private String name;				// FP 이름
	private String customersPublicYn;   // 고객 수 공개여부
	private String contractsPublicYn;   // 계약 수 공개여부
	private String contractRatePublicYn;   // 계약 유지율 공개여부
	private String imperfectRatePublicYn;   // 불완전판매율 공개여부

	public static ECardContractSettingsDto of(ECardBean bean) {
		return new ECardContractSettingsDto(
			bean.getEcardId(),
			bean.getName(),
			bean.getCustomersPublicYn(),
			bean.getContractsPublicYn(),
			bean.getContractRatePublicYn(),
			bean.getImperfectRatePublicYn()
		);
	}


	public ECardContractSettingsDto(
		String ecardId,
		String customersPublicYn,
		String contractsPublicYn,
		String contractRatePublicYn,
		String imperfectRatePublicYn
	) {
		this.ecardId = ecardId;
		this.customersPublicYn = customersPublicYn;
		this.contractsPublicYn = contractsPublicYn;
		this.contractRatePublicYn = contractRatePublicYn;
		this.imperfectRatePublicYn = imperfectRatePublicYn;
	}

	public ECardContractSettingsDto(
			String ecardId,
			String name,
			String customersPublicYn,
			String contractsPublicYn,
			String contractRatePublicYn,
			String imperfectRatePublicYn
	) {
		this.ecardId = ecardId;
		this.name = name;
		this.customersPublicYn = customersPublicYn;
		this.contractsPublicYn = contractsPublicYn;
		this.contractRatePublicYn = contractRatePublicYn;
		this.imperfectRatePublicYn = imperfectRatePublicYn;
	}
}