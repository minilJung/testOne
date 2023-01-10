package com.ebc.ecard.application.career.dto;

public class CareerFilterDto {

    protected String userId;
    protected String publicYn;

    public CareerFilterDto(String userId, String publicYn) {
        this.userId = userId;
        this.publicYn = publicYn;
    }

    public CareerFilterDto(String userId) {
        this.userId = userId;
    }
}
