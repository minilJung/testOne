package com.ebc.ecard.domain.scraping;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScrapingBean {

    protected String scrapingId;
    protected String ecardId;
    protected String requestData;
    protected String callbackData;
    protected String responseMessage;
    protected String status;
    protected String scrapingType;
    protected Date createdAt;
    protected Date updatedAt;


    public ScrapingBean(
        String scrapingId,
        String ecardId,
        String requestData,
        String callbackData,
        String responseMessage,
        String status,
        String scrapingType,
        Date createdAt,
        Date updatedAt
    ) {
        this.scrapingId = scrapingId;
        this.ecardId = ecardId;
        this.requestData = requestData;
        this.callbackData = callbackData;
        this.responseMessage = responseMessage;
        this.status = status;
        this.scrapingType = scrapingType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
