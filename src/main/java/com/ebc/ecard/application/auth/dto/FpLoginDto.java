package com.ebc.ecard.application.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FP용 로그인 Dto - 2022.06.23
 * @author jgpark
 */
@NoArgsConstructor
@Getter
@Setter
public class FpLoginDto {
    protected String fpUniqNo;
}
