package com.ebc.ecard.application.qualification.dto;

public class QualificationFilterDto {

    protected String userId;
    protected String publicYn;

    public QualificationFilterDto(String userId, String publicYn) {
        this.userId = userId;
        this.publicYn = publicYn;
    }
}
