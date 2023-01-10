package com.ebc.ecard.domain.activity;

import com.ebc.ecard.util.BaseBean;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEducationBean extends BaseBean {
    protected String educationId;
    protected String userId;
    protected String educationName;
    protected String educationOrganizationName;
    protected Date startDate;
    protected Date endDate;
    protected String publicYn;
    //protected ApprovalStatus status;
    protected Date createdAt;
    protected Date approvedAt;

    public UserEducationBean(
        String educationId,
        String userId,
        String educationName,
        String educationOrganizationName,
        Date startDate,
        Date endDate,
        String publicYn,
        //ApprovalStatus status,
        Date createdAt,
        Date approvedAt
    ) {
        this.educationId = educationId;
        this.userId = userId;
        this.educationName = educationName;
        this.educationOrganizationName = educationOrganizationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.publicYn = publicYn;
        //this.status = status;
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
    }
}