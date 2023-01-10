package com.ebc.ecard.application.nftcv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * NFT CV 상담가능 보험사 생성 시 필요한 DTO
 * @author wnguds1101
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NFTCvConsultingCompanyDto {
    protected String companyName;   //이름
    protected String companyType;   //보험사 구분
    protected String companyImg;    //로고 이미지

}
