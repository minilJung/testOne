package com.ebc.ecard.application.user.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAddUsingMandatoryRequestDto {
    private List<UserAddUsingMandatoryDto> list;

    public UserAddUsingMandatoryRequestDto(List<UserAddUsingMandatoryDto> list) {
        this.list = list;
    }
}