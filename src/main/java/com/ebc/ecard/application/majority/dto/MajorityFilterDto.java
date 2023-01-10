package com.ebc.ecard.application.majority.dto;

import lombok.Getter;

@Getter
public class MajorityFilterDto {
    protected String userId;
    protected String publicYn;

    public MajorityFilterDto(String userId, String publicYn) {
        this.userId = userId;
        this.publicYn = publicYn;
    }
}