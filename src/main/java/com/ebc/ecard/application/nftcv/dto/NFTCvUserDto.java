package com.ebc.ecard.application.nftcv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NFTCvUserDto {

    private String userId;
    private String companyId;
    private String ecardId;
    private String fpId;

    private String name;
    private String password;
    private String accountId;
    private String birthdate;
    private String di;
    private String ci;
    private String mobileNo;
    private String email;
}
