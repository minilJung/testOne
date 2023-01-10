package com.ebc.ecard.application.ecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardCompletionRateDto {
    protected int totalCount;
    protected int completedCount;
    protected double completionRate;
    protected ECardCompletionPropertiesDto properties;

    public ECardCompletionRateDto(
            int totalCount,
            int completedCount,
            double completionRate,
            ECardCompletionPropertiesDto properties
    ) {
        this.totalCount = totalCount;
        this.completedCount = completedCount;
        this.completionRate = completionRate;
        this.properties = properties;
    }
}
