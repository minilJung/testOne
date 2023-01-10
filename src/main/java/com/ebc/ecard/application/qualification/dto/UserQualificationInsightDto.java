package com.ebc.ecard.application.qualification.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserQualificationInsightDto {

    protected boolean existData;

    protected boolean existPublic;

    protected boolean allPublic;

    protected List<UserQualificationDto> qualifications;

    public static UserQualificationInsightDto of(QualificationInsight insight, List<UserQualificationDto> qualifications) {
        return new UserQualificationInsightDto(
            insight.isExistData(),
            insight.isExistPublic(),
            insight.isAllPublic(),
            qualifications
        );
    }

    public UserQualificationInsightDto(
        boolean existData,
        boolean existPublic,
        boolean allPublic,
        List<UserQualificationDto> qualifications
    ) {
        this.existData = existData;
        this.existPublic = existPublic;
        this.allPublic = allPublic;
        this.qualifications = qualifications;

    }
}