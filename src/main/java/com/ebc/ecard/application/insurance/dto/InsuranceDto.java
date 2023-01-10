package com.ebc.ecard.application.insurance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 판매 가능 보험사 - 2022.06.23
 * @author jgpark
 */
@NoArgsConstructor
@Getter
@Setter
public class InsuranceDto {
    protected String isrnCmpyCode;
    protected String isrnCmpyNm;
    protected String isrnCmpyLogo;
}
