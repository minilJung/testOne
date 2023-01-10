package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.application.dto.ResponseDtoInterface;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ECardProfileCaptureResponseDto implements ResponseDtoInterface {

    @JsonProperty("image")
    protected Map<String, Object> result;

    protected String responseTime;

    protected String requestId;
}
