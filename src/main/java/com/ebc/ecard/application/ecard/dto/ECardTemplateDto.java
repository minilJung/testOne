package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.domain.ecard.ECardTemplateBean;
import com.ebc.ecard.util.BaseBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardTemplateDto extends BaseBean {
	protected String templateNo;
	protected String backgroundImgUrl;
	protected String nameColor;
	protected String mobileNumberColor;
	protected String regularTextColor;
	protected String logoImageType;
	protected String companyLogo;
	protected String badgeImageType;

	public static ECardTemplateDto fromEntity(@NonNull ECardTemplateBean bean) {

		return new ECardTemplateDto(
			bean.getTemplateNo(),
			bean.getBackgroundImgUrl(),
			bean.getNameColor(),
			bean.getMobileNumberColor(),
			bean.getRegularTextColor(),
			bean.getLogoImageType(),
			bean.getCompanyLogo(),
			bean.getBadgeImageType()
		);
	}

	public ECardTemplateDto(
		String templateNo,
		String backgroundImgUrl,
		String nameColor,
		String mobileNumberColor,
		String regularTextColor,
		String logoImageType,
		String companyLogo,
		String badgeImageType
	) {
		this.templateNo = templateNo;
		this.backgroundImgUrl = backgroundImgUrl;
		this.nameColor = nameColor;
		this.mobileNumberColor = mobileNumberColor;
		this.regularTextColor = regularTextColor;
		this.logoImageType = logoImageType;
		this.companyLogo = companyLogo;
		this.badgeImageType = badgeImageType;
	}
}