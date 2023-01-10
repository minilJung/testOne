package com.ebc.ecard.application.ecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardUserExistenceDto {
    protected String membJoinYn;
    protected String fpId;
    protected String ecardId;
    protected String userId;
    protected String name;
    protected String ci;
    protected String accountId;
    protected String elctNmcdUrlAddr;

    public static ECardUserExistenceDto ofNotExists() {
        return new ECardUserExistenceDto(
            "N",
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
    }

    public ECardUserExistenceDto(
        String membJoinYn,
        String fpId,
        String ecardId,
        String userId,
        String name,
        String ci,
        String accountId,
        String elctNmcdUrlAddr
    ) {
        this.membJoinYn = membJoinYn;
        this.fpId = fpId;
        this.ecardId = ecardId;
        this.userId = userId;
        this.name = name;
        this.ci = ci;
        this.accountId = accountId;
        this.elctNmcdUrlAddr = elctNmcdUrlAddr;
    }
}
