package com.ebc.ecard.application.user.dto;

import com.ebc.ecard.application.dto.RequestDtoInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class EBAPRequestDto implements RequestDtoInterface<String, String> {
    protected String id;
    protected String serviceId;

    @Override
    public Map<String, String> convertToMap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("serviceId", serviceId);

        return map;
    }

    public EBAPRequestDto(String id, String serviceId) {
        this.id = id;
        this.serviceId = serviceId;
    }
}
