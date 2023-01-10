package com.ebc.ecard.domain.ecard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardTemplateBean {
	protected String templateNo;
	protected String backgroundImgUrl;
	protected String nameColor;
	protected String mobileNumberColor;
	protected String regularTextColor;
	protected String logoImageType;
	protected String companyLogo;
	protected String badgeImageType;

	public ECardTemplateBean(
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