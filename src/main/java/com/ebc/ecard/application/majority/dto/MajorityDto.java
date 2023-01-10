package com.ebc.ecard.application.majority.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MajorityDto {
    protected String majorityId;
    protected String majorityName;

    public MajorityDto(String majorityId, String majorityName) {
        this.majorityId = majorityId;
        this.majorityName = majorityName;
    }
}
