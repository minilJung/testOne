package com.ebc.ecard.domain.majority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserMajorityBean {
    protected String majorityId;
    protected String majorityName;
    protected String userId;
    protected String publicYn;

    public UserMajorityBean(String majorityId, String majorityName, String userId, String publicYn) {
        this.majorityId = majorityId;
        this.majorityName = majorityName;
        this.userId = userId;
        this.publicYn = publicYn;
    }
}
