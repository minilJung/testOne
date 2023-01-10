package com.ebc.ecard.application.dto;

import java.util.Map;

public interface RequestDtoInterface<K, V> {
    Map<K, V> convertToMap();
}
