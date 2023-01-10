package com.ebc.ecard.domain.qualification;

import java.time.Instant;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationBean {
    protected String registrationId;
    protected String userId;
    protected String registrationFileId;
    protected String fileName;
    protected String publicYn;
    protected Date createdAt;
    protected Date lastUpdatedAt;

    public UserRegistrationBean(
        String registrationId,
        String userId,
        String registrationFileId,
        String publicYn,
        Date createdAt,
        Date lastUpdatedAt
    ) {
        this.registrationId = registrationId;
        this.userId = userId;
        this.registrationFileId = registrationFileId;
        this.publicYn = publicYn;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
