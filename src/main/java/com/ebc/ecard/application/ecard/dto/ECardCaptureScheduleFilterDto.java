package com.ebc.ecard.application.ecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardCaptureScheduleFilterDto {

    private int chunk;

    public ECardCaptureScheduleFilterDto(int chunk) {
        this.chunk = chunk;
    }
}
