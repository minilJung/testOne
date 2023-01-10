package com.ebc.ecard.application.ecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaotalkApiKeyDto {

    protected String encryptedAppId;

    protected String encryptedJsKey;

    public KakaotalkApiKeyDto(String encryptedAppId, String encryptedJsKey) {
        this.encryptedAppId = encryptedAppId;
        this.encryptedJsKey = encryptedJsKey;
    }
}
