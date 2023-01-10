package com.ebc.ecard.application.majority.dto;

import com.ebc.ecard.domain.majority.UserMajorityBean;

import lombok.Getter;

@Getter
public class UserMajorityDto {
    protected String majorityId;
    protected String majorityName;
    protected String publicYn;

    public static UserMajorityDto fromBean(UserMajorityBean bean) {
        return new UserMajorityDto(
            bean.getMajorityId(),
            bean.getMajorityName(),
            bean.getPublicYn()
        );
    }

    public UserMajorityDto(String majorityId, String majorityName, String publicYn) {
        this.majorityId = majorityId;
        this.majorityName = majorityName;
        this.publicYn = publicYn;
    }
}
