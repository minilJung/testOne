package com.ebc.ecard.application.nftcv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * NFT CV 수상 내역 생성 시 필요한 DTO
 * @author wnguds1101
 */
@Getter
@Setter
@NoArgsConstructor
public class NFTCvAwardDto {
    protected String awardsCode;            // 수상 키 값
    protected String awardsInstituteName;   // 수상 기관
    protected String awardsName;            // 수상명
    protected String awardsDate;            // 수상일
}
