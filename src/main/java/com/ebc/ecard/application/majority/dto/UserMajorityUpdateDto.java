package com.ebc.ecard.application.majority.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserMajorityUpdateDto {

    protected String majorityId;

    protected String publicYn;

    public UserMajorityUpdateDto(String majorityId, String publicYn) {
        this.majorityId = majorityId;
        this.publicYn = publicYn;
    }
}
