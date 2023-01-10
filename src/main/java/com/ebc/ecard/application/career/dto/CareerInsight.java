package com.ebc.ecard.application.career.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CareerInsight {

    protected boolean existData;

    protected boolean existPublic;

    protected boolean allPublic;

    protected int workDays;

    public CareerInsight(
        boolean existData,
        boolean existPublic,
        boolean allPublic,
        int workDays
    ) {
        this.existData = existData;
        this.existPublic = existPublic;
        this.allPublic = allPublic;
        this.workDays = workDays;
    }
}