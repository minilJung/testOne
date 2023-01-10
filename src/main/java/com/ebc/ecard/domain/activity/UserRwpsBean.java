package com.ebc.ecard.domain.activity;

import com.ebc.ecard.util.BaseBean;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRwpsBean extends BaseBean {
    protected String rwpsId;
    protected String userId;
    protected String rwpsBrocName; // 수상기관명
    protected String rwpsCntnName; // 수상명
    protected String publicYn;
    //protected ApprovalStatus status;
    protected Date rwpsStarDate;
    protected Date createdAt;
    protected Date approvedAt;
    String[] fileIds;

    public UserRwpsBean(
        String rwpsId,
        String userId,
        String rwpsBrocName,
        String rwpsCntnName,
        String publicYn,
        //ApprovalStatus status,
        Date rwpsStarDate,
        Date createdAt,
        Date approvedAt
    ) {
        this.rwpsId = rwpsId;
        this.userId = userId;
        this.rwpsBrocName = rwpsBrocName;
        this.rwpsCntnName = rwpsCntnName;
        this.publicYn = publicYn;
        //this.status = status;
        this.rwpsStarDate = rwpsStarDate;
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
    }
}