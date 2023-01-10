package com.ebc.ecard.application.qualification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QualificationInsight {

    protected boolean existData;

    protected boolean existPublic;

    protected boolean allPublic;

    public QualificationInsight(
        boolean existData,
        boolean existPublic,
        boolean allPublic
    ) {
        this.existData = existData;
        this.existPublic = existPublic;
        this.allPublic = allPublic;
    }
}