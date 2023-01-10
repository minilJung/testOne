package com.ebc.ecard.application.scraping.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScrapingDto {
    protected String scrapingId;
    protected String ecardId;
    protected String requestData;
    protected String callbackData;
    protected String responseMessage;
    protected String status;

    public ScrapingDto(String scrapingId, String ecardId, String requestData, String status) {
        this.scrapingId = scrapingId;
        this.ecardId = ecardId;
        this.requestData = requestData;
        this.status = status;
    }

    public ScrapingDto(String scrapingId, String requestData, String callbackData, String responseMessage, String status) {
        this.scrapingId = scrapingId;
        this.requestData = requestData;
        this.callbackData = callbackData;
        this.status = status;
    }
}
