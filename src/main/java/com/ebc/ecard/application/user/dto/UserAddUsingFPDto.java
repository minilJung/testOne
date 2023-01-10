package com.ebc.ecard.application.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAddUsingFPDto {
	private String fpUniqNo;		// 사번
	private String name;

	public UserAddUsingFPDto(String fpId, String name) {
		this.fpUniqNo = fpId;
		this.name = name;
	}
}