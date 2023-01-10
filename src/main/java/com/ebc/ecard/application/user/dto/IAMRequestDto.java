package com.ebc.ecard.application.user.dto;

import com.ebc.ecard.application.dto.RequestDtoInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class IAMRequestDto implements RequestDtoInterface<String, Object> {
    protected String name;
    protected String userId;
    protected String ci;

    @Override
    public Map<String, Object> convertToMap() {
        Map<String, Object> inputItem = new HashMap<>();
        Map<String, Object> userInfoItem = new HashMap<>();

        userInfoItem.put("name", name);
        userInfoItem.put("userId", userId);
        userInfoItem.put("ci", ci);

        inputItem.put("user", userInfoItem);

        return inputItem;
    }

    public IAMRequestDto(String name, String userId, String ci) {
        this.name = name;
        this.userId = userId;
        this.ci = ci;
    }
}
