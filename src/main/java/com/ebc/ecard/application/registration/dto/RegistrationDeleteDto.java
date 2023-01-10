package com.ebc.ecard.application.registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationDeleteDto {
    protected String userId;

    protected String registrationFileId;

    public RegistrationDeleteDto(String userId, String registrationFileId) {
        this.userId = userId;
        this.registrationFileId = registrationFileId;
    }
}
