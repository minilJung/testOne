package com.ebc.ecard.application.agreement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAgreementAddDto {
    protected String userId;
    protected String[] agreementNames;

    public UserAgreementAddDto(String userId, String[] agreementNames) {
        this.userId = userId;
        this.agreementNames = agreementNames;
    }

    public UserAgreementAddDto(String[] agreementNames) {
        this.agreementNames = agreementNames;
    }

}
