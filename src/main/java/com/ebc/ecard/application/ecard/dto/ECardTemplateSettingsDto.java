package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.domain.ecard.ECardTemplateBean;
import com.ebc.ecard.util.BaseBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardTemplateSettingsDto extends BaseBean {
	private String ecardId;             // 전자명함 아이디
	private ECardTemplateDto template;
	private String templateNo;
	private String branchNumberPublicYn;
	private String faxNumberPublicYn;
	private String badgePublicYn;

	public static ECardTemplateSettingsDto fromBean(ECardBean bean, ECardTemplateBean templateBean) {

		return new ECardTemplateSettingsDto(
			bean.getEcardId(),
			(templateBean == null) ? null : ECardTemplateDto.fromEntity(templateBean),
			bean.getBranchNumberPublicYn(),
			bean.getFaxNumberPublicYn(),
			bean.getBadgePublicYn()
		);
	}


	public ECardTemplateSettingsDto(
		String ecardId,
		ECardTemplateDto template,
		String branchNumberPublicYn,
		String faxNumberPublicYn,
		String badgePublicYn
	) {
		this.ecardId = ecardId;
		this.template = template;
		this.branchNumberPublicYn = branchNumberPublicYn;
		this.faxNumberPublicYn = faxNumberPublicYn;
		this.badgePublicYn = badgePublicYn;
	}
}