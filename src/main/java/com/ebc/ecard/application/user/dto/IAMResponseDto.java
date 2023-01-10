package com.ebc.ecard.application.user.dto;

import com.ebc.ecard.application.dto.ResponseDtoInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IAMResponseDto implements ResponseDtoInterface {
    protected IAMInfoDto data;
}
