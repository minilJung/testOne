package com.ebc.ecard.application.user.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAddUsingMandatoryDto {
	private String name;
	private String mobileNo;
	private String email;
	private Date birthdate;

	public UserAddUsingMandatoryDto(String name, String mobileNo, String email, Date birthdate) {
		this.name = name;
		this.mobileNo = mobileNo;
		this.email = email;
		this.birthdate = birthdate;
	}
}