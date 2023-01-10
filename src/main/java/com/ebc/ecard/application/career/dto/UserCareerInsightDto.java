package com.ebc.ecard.application.career.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCareerInsightDto {

    protected boolean existData;

    protected boolean existPublic;

    protected boolean allPublic;

    protected String careerDescription;

    protected List<UserCareerDto> careers;

    public static UserCareerInsightDto of(CareerInsight insight, List<UserCareerDto> careers) {

        String careerDescription = "0개월";
        if (insight.getWorkDays() > 0) {
            int months = (insight.getWorkDays() / 30) % 12;
            int years = (insight.getWorkDays() / 30) / 12;

            careerDescription = ((years > 0) ? years + "년 " : "") + months + "개월";
        }

        return new UserCareerInsightDto(
            insight.isExistData(),
            insight.isExistPublic(),
            insight.isAllPublic(),
            careerDescription,
            careers
        );
    }


    public UserCareerInsightDto(
        boolean existData,
        boolean existPublic,
        boolean allPublic,
        String careerDescription,
        List<UserCareerDto> careers
    ) {
        this.existData = existData;
        this.existPublic = existPublic;
        this.allPublic = allPublic;
        this.careerDescription = careerDescription;
        this.careers = careers;

    }
}