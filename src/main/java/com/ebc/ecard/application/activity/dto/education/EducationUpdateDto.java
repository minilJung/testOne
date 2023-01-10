package com.ebc.ecard.application.activity.dto.education;

import com.ebc.ecard.domain.value.ApprovalStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EducationUpdateDto {
    protected String educationId;
    //protected ApprovalStatus status;
    protected String publicYn;

    public EducationUpdateDto(String educationId, ApprovalStatus status, String publicYn) {
        this.educationId = educationId;
        //this.status = status;
        this.publicYn = publicYn;
    }
}
