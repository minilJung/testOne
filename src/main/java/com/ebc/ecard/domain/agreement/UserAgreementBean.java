package com.ebc.ecard.domain.agreement;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAgreementBean {
    protected String agreementId;
    protected String agreementName;
    protected String userId;
    protected Date createdAt;

    public UserAgreementBean(String agreementId, String agreementName, String userId, Date createdAt) {
        this.agreementId = agreementId;
        this.agreementName = agreementName;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
