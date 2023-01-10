package com.ebc.ecard.application.exception;

public class ScrapingFailedException extends RuntimeException {

    public ScrapingFailedException() {
        super("경력/자격증 정보 업데이트 실패");
    }

    public ScrapingFailedException(String message) {
        super("경력/자격증 정보 업데이트 실패, " + message);
    }
}
