package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.domain.ecard.ECardBean;

import java.util.Map;

import lombok.Getter;

@Getter
public class ECardUserDto {

    protected String ecardId;
    protected String fpId;
    protected String userId;

    public static ECardUserDto fromBean(ECardBean bean) {
        return new ECardUserDto(
            bean.getEcardId(),
            bean.getFpId(),
            bean.getUserId()
        );
    }

    public static ECardUserDto fromMap(Map<String, Object> map) {
        return new ECardUserDto(
            (String) map.get("ecardId"),
            (String) map.get("fpId"),
            (String) map.get("userId")
        );
    }

    public ECardUserDto(String ecardId, String fpId, String userId) {
        this.ecardId = ecardId;
        this.fpId = fpId;
        this.userId = userId;
    }

}
