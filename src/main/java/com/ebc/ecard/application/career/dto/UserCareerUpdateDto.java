package com.ebc.ecard.application.career.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCareerUpdateDto {
    protected String careerId;
    protected String publicYn;

    public UserCareerUpdateDto(String careerId, String publicYn) {
        this.careerId = careerId;
        this.publicYn = publicYn;
    }

}
