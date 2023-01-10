package com.ebc.ecard.application.activity.dto;

import com.ebc.ecard.domain.activity.value.ActivityType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActivityUpdateRequestDto {

    protected String id;

    protected ActivityType type;

    protected String publicYn;

}
