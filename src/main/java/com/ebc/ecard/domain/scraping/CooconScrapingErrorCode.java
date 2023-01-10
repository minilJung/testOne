package com.ebc.ecard.domain.scraping;

public enum CooconScrapingErrorCode {
    SUCCESS("00000000"),
    RESULT_NONE("42110000");

    private String value;

    CooconScrapingErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CooconScrapingErrorCode[] getSuccessCodes() {

        return new CooconScrapingErrorCode[]{ SUCCESS, RESULT_NONE };
    }

}
