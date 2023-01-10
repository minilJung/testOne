package com.ebc.ecard.application.majority.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserMajorityInsightDto {

    protected boolean existData;

    protected boolean existPublic;

    protected boolean allPublic;

    protected List<UserMajorityDto> majorities;

    public UserMajorityInsightDto(
        boolean existData, boolean existPublic, boolean allPublic, List<UserMajorityDto> majorities
    ) {
        this.existData = existData;
        this.existPublic = existPublic;
        this.allPublic = allPublic;
        this.majorities = majorities;
    }
}
