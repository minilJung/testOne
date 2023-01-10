package com.ebc.ecard.application.checkplus.dto;

import com.ebc.ecard.application.dto.RequestDtoInterface;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class CheckplusTokenRequestDataBodyDto implements RequestDtoInterface<String, String> {
    protected String requestDatetime;

    protected String requestNo;

    protected final String encMode = "1";

    public CheckplusTokenRequestDataBodyDto(String requestDatetime, String requestNo) {
        this.requestDatetime = requestDatetime;
        this.requestNo = requestNo;
    }

    @Override
    public Map<String, String> convertToMap() {
        Map<String, String> map = new HashMap<>();
        map.put("req_dtim", requestDatetime);
        map.put("req_no", requestNo);
        map.put("enc_mode", encMode);

        return map;
    }
}

