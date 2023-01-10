package com.ebc.ecard.application.checkplus.dto;

import java.util.Date;

import lombok.Getter;

@Getter
public class EcardUiCheckplusDto {

    protected CheckplusTokenResponseDataBodyDto dataBody;

    protected String key;
    protected String iv;
    protected String hmacKey;

    protected Date expireAt;

    public EcardUiCheckplusDto(CheckplusTokenResponseDataBodyDto dataBody, String key, String iv, String hmacKey) {
        this.dataBody = dataBody;
        this.key = key;
        this.iv = iv;
        this.hmacKey = hmacKey;

        this.expireAt = new Date(new Date().getTime() + ((long) Double.parseDouble(dataBody.getPeriod()) * 1000));
    }
}
