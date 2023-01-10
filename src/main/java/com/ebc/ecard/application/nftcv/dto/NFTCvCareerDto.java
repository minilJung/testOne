package com.ebc.ecard.application.nftcv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * NFT CV 경력 생성 시 필요한 DTO
 * @author wnguds1101
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NFTCvCareerDto {
    protected String policyholderCode;  // 가입자 구분
    protected String workplaceName;     // 사업장 명칭
    protected String startDate;         // 취득일(입사일)
    protected String endDate;           // 상실일(퇴사일)
}
