package com.ebc.ecard.application.registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationUpdateDto {
    protected String registrationId;
    protected String userId;
    protected String registrationFileId;
    protected String publicYn;
}
