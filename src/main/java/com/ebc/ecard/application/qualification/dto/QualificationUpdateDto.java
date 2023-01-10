package com.ebc.ecard.application.qualification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QualificationUpdateDto {
    protected String payload;

    public QualificationUpdateDto(String payload) {
        this.payload = payload;
    }
}
