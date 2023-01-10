package com.ebc.ecard.application.nftcv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * NFT CV 교육 생성 시 필요한 DTO
 * @author wnguds1101
 */
@Getter
@Setter
@NoArgsConstructor
public class NFTCvEducationDto {
    protected String trainingId;                // 교육 키 값
    protected String trainingInstituteName;     // 교육기관명
    protected String trainingName;              // 교육명
    protected String startDate;                 // 교육시작일
    protected String endDate;                   // 교육마감일
}
