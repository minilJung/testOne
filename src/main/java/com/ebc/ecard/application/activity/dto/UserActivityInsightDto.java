package com.ebc.ecard.application.activity.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserActivityInsightDto {

    protected boolean existData;

    protected boolean existPublic;

    protected boolean allPublic;

    protected List<UserActivityDto> activities;

    public static UserActivityInsightDto of(ActivityInsight insight, List<UserActivityDto> activities) {

        return new UserActivityInsightDto(
            insight.isExistData(),
            insight.isExistPublic(),
            insight.isAllPublic(),
            activities
        );
    }

    public UserActivityInsightDto(
        boolean existData, boolean existPublic, boolean allPublic, List<UserActivityDto> activities
    ) {
        this.existData = existData;
        this.existPublic = existPublic;
        this.allPublic = allPublic;
        this.activities = activities;
    }
}
