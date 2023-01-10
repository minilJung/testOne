package com.ebc.ecard.application.activity.dto;

import com.ebc.ecard.domain.activity.value.ActivityType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActivityDeleteRequestDto {

    protected String id;

    protected ActivityType type;

    public ActivityDeleteRequestDto(String id, ActivityType type) {
        this.id = id;
        this.type = type;
    }
}
