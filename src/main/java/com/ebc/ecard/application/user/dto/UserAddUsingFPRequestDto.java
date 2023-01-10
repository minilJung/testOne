package com.ebc.ecard.application.user.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAddUsingFPRequestDto {
	private List<UserAddUsingFPDto> list;

	public UserAddUsingFPRequestDto(List<UserAddUsingFPDto> list) {
		this.list = list;
	}
}