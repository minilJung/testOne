package com.ebc.ecard.application.checkplus.dto;

import com.ebc.ecard.application.dto.RequestDtoInterface;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckplusDataDto<H, B> implements RequestDtoInterface<String, Object> {
    protected H dataHeader;
    protected B dataBody;

    public CheckplusDataDto(H dataHeader, B dataBody) {
        this.dataHeader = dataHeader;
        this.dataBody = dataBody;
    }

    @Override
    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();

        if (dataHeader instanceof RequestDtoInterface)
            map.put("dataHeader", ((RequestDtoInterface<?, ?>) dataHeader).convertToMap());
        else
            map.put("dataHeader", dataHeader);

        if (dataBody instanceof RequestDtoInterface)
            map.put("dataBody", ((RequestDtoInterface<?, ?>) dataBody).convertToMap());
        else
            map.put("dataBody", dataBody);

        return map;
    }


}
