package com.ebc.ecard.application.nftcv.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NFTCvDto {
    protected String position;
    protected String branchOffice;
    protected String currentlyEmployed;

    protected List<NFTCvConsultingCompanyDto> consultingCompanyList;
    protected NFTCvContractInfoDto contractStatus;
    protected List<NFTCvGoodFpRwpDto> goodFPAwardList;
    protected List<NFTCvEducationDto> trainingList;
    protected List<NFTCvAwardDto> awardsList;
    protected List<NFTCvCareerDto> careerList;
    protected List<NFTCvQualificationsDto> licenseList;

}
