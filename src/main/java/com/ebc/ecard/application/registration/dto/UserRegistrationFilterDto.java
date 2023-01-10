package com.ebc.ecard.application.registration.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationFilterDto {
    protected String userId;
    protected String publicYn;

    public UserRegistrationFilterDto(
        String userId,
        String publicYn
    ) {
        this.userId = userId;
        this.publicYn = publicYn;
    }

}
