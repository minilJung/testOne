package com.ebc.ecard.application.checkplus.dto;

import com.ebc.ecard.application.dto.RequestDtoInterface;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckplusTokenRequestDataHeaderDto implements RequestDtoInterface<String, String> {
    protected final String CNTY_CD = "ko";

    @Override
    public Map<String, String> convertToMap() {
        Map<String, String> map = new HashMap<>();
        map.put("CNTY_CD", CNTY_CD);

        return map;
    }
}

