package com.ebc.ecard.application.activity.dto.rwps;

import com.ebc.ecard.domain.value.ApprovalStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RwpsUpdateDto {
    protected String rwpsId;
    //protected ApprovalStatus status;
    protected String publicYn;

    public RwpsUpdateDto(String rwpsId, ApprovalStatus status, String publicYn) {
        this.rwpsId = rwpsId;
        //this.status = status;
        this.publicYn = publicYn;
    }
}
