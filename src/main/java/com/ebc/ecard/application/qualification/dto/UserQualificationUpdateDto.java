package com.ebc.ecard.application.qualification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserQualificationUpdateDto {
    protected String qualificationId;
    protected String publicYn;

    public UserQualificationUpdateDto(String qualificationId, String publicYn) {
        this.qualificationId = qualificationId;
        this.publicYn = publicYn;
    }

}
