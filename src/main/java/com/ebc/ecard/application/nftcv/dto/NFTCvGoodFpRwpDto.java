package com.ebc.ecard.application.nftcv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * NFT CV 우수 인증 수상 내역 생성 시 필요한 DTO
 * @author wnguds1101
 */
@Getter
@Setter
@NoArgsConstructor
public class NFTCvGoodFpRwpDto {
    protected String cmpyName;      // 실적 회사
    protected String regDate;       // 실적 년도
    protected String certiCode ;    // 인증번호
}
