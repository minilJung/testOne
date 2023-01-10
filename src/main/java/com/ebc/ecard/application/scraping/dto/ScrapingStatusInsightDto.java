package com.ebc.ecard.application.scraping.dto;

import com.ebc.ecard.domain.scraping.ScrapingBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScrapingStatusInsightDto {
    protected String scrapingId;
    protected String message;
    protected String status;

    public static ScrapingStatusInsightDto from(ScrapingBean bean) {
        return new ScrapingStatusInsightDto(
            bean.getScrapingId(),
            bean.getResponseMessage(),
            bean.getStatus()
        );
    }

    public ScrapingStatusInsightDto(String scrapingId, String message, String status) {
        this.scrapingId = scrapingId;
        this.message = message;
        this.status = status;
    }
}
