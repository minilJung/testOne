package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.application.dto.RequestDtoInterface;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 CV Profile 정보 조회 Request Parameters Dto - 2022.06.27
 * @author jgpark
 */
@NoArgsConstructor
@Getter
@Setter
public class EmployeeCvInfoRequestDto implements RequestDtoInterface<String, String> {
    protected String fpId;

    public EmployeeCvInfoRequestDto(String fpId) {
        this.fpId = fpId;
    }

    @Override
    public Map<String, String> convertToMap() {
        Map<String, String> map = new HashMap<>();
        map.put("fpUniqNo", fpId);

        return map;
    }
}
