package com.ebc.ecard.application.scraping.dto;

import lombok.Getter;

@Getter
public class PopupRequestFormDataDto {
    protected String requestData;
    protected String requestVid;
    protected String orgCode;
    protected String scrapingId;
    protected String cooconUrl;

    public PopupRequestFormDataDto(String requestData, String requestVid, String orgCode, String scrapingId, String cooconUrl) {
        this.requestData = requestData;
        this.requestVid = requestVid;
        this.orgCode = orgCode;
        this.scrapingId = scrapingId;
        this.cooconUrl = cooconUrl;
    }
}
