package com.ebc.ecard.advice;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class HttpExceptionResponseDto {

    private String timestamp;
    private int status;
    private String error;
    private String message;

    public HttpExceptionResponseDto(String timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }
}