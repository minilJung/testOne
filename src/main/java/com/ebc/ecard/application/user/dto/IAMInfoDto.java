package com.ebc.ecard.application.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IAMInfoDto {
    protected String userNid;
    protected String userId;    // 한금서 fpId
    protected String accountId;
}
