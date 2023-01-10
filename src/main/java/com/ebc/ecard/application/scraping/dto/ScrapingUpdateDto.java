package com.ebc.ecard.application.scraping.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScrapingUpdateDto {
    protected String scrapingId;
    protected String callbackData;
    protected String responseMessage;
    protected String status;

    public ScrapingUpdateDto(String scrapingId, String callbackData, String responseMessage, String status) {
        this.scrapingId = scrapingId;
        this.callbackData = callbackData;
        this.responseMessage = responseMessage;
        this.status = status;
    }
}
