package com.ebc.ecard.application.career.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CareerUpdateDto {
    protected String payload;

    public CareerUpdateDto(String payload) {
        this.payload = payload;
    }
}
