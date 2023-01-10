package com.ebc.ecard.application.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserExistenceDto {
    protected String membJoinYn;
    protected String elctNmcdUrlAddr;

    public UserExistenceDto(String membJoinYn, String elctNmcdUrlAddr) {
        this.membJoinYn = membJoinYn;
        this.elctNmcdUrlAddr = elctNmcdUrlAddr;
    }
}
