package com.ebc.ecard.application.dto;

import com.ebc.ecard.application.dto.RequestDtoInterface;

import java.util.HashMap;
import java.util.Map;

public class EmptyRequestDto implements RequestDtoInterface<Object, Object> {

    @Override
    public Map<Object, Object> convertToMap() {
        return new HashMap<>();
    }
}
