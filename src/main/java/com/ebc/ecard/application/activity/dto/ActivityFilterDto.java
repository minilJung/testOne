package com.ebc.ecard.application.activity.dto;

import com.ebc.ecard.domain.value.ApprovalStatus;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActivityFilterDto {

    protected String userId;
    protected String publicYn;
    //protected List<ApprovalStatus> statuses;

    public ActivityFilterDto(String userId, String publicYn) {
        this.userId = userId;
        this.publicYn = publicYn;
    }

    public ActivityFilterDto(String userId, String publicYn, List<ApprovalStatus> statuses) {
        this.userId = userId;
        this.publicYn = publicYn;
        //this.statuses = statuses;
    }
}
