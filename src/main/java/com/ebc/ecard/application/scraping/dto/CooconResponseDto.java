package com.ebc.ecard.application.scraping.dto;

import com.ebc.ecard.application.dto.ResponseDtoInterface;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CooconResponseDto implements ResponseDtoInterface {

    @JsonProperty("ResultList")
    protected List<Map<String, Object>> resultList;

    @JsonProperty("Common")
    protected Map<String, Object> common;

    public CooconResponseDto(List<Map<String, Object>> resultList, Map<String, Object> common) {
        this.resultList = resultList;
        this.common = common;
    }
}
