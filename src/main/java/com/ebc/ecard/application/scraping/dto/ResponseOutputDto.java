package com.ebc.ecard.application.scraping.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ResponseOutputDto {

    protected String ErrorCode;
    protected String ErrorMessage;
    protected Map<String, String> Result;

    public ResponseOutputDto(String errorCode, String errorMessage, Map<String, String> result) {
        ErrorCode = errorCode;
        ErrorMessage = errorMessage;
        Result = result;
    }
}
