package com.ebc.ecard.application.nftcv.dto;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class NFTCvQualificationsDto {
    protected String licenseName;       // 자격증 명
    protected String licenseIssuer;     // 발급단체
    protected String licenseNumber;    // 자격증 번호
    protected String passDate;          // 최종합격일자
}
