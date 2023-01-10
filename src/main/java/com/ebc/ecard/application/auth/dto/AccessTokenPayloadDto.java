package com.ebc.ecard.application.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccessTokenPayloadDto {
    protected String userId;

    public AccessTokenPayloadDto(String userId) {
        this.userId = userId;
    }
}
