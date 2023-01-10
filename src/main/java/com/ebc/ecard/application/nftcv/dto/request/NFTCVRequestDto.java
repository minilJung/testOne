package com.ebc.ecard.application.nftcv.dto.request;

import com.ebc.ecard.application.contract.dto.ContractInfoDto;
import com.ebc.ecard.application.contract.dto.EmployeeContractInfoResponseDto;
import com.ebc.ecard.application.dto.RequestDtoInterface;
import com.ebc.ecard.application.ecard.dto.EmployeeCvInfoResponseDto;
import com.ebc.ecard.application.nftcv.dto.NFTCvAwardDto;
import com.ebc.ecard.application.nftcv.dto.NFTCvCareerDto;
import com.ebc.ecard.application.nftcv.dto.NFTCvConsultingCompanyDto;
import com.ebc.ecard.application.nftcv.dto.NFTCvContractInfoDto;
import com.ebc.ecard.application.nftcv.dto.NFTCvDto;
import com.ebc.ecard.application.nftcv.dto.NFTCvEducationDto;
import com.ebc.ecard.application.nftcv.dto.NFTCvGoodFpRwpDto;
import com.ebc.ecard.application.nftcv.dto.NFTCvQualificationsDto;
import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.domain.insurance.value.InsuranceType;
import com.ebc.ecard.domain.user.UserBean;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NFTCVRequestDto extends NFTCvDto implements RequestDtoInterface<String, Object> {
    protected String callbackUri;

    public static NFTCVRequestDto of(String callbackUrl, EmployeeCvInfoResponseDto cvInfoDto) {
        List<NFTCvConsultingCompanyDto> consultingCompanyList =
            (cvInfoDto.getCuslPssbIscm() != null)
                ? cvInfoDto.getCuslPssbIscm().stream().map(
                    it ->
                        new NFTCvConsultingCompanyDto(
                            it.getIscmNm(),
                            it.getIscmCode().contains("L") ? InsuranceType.LIFE.getValue() : InsuranceType.GENERAL.getValue(),
                            "/img/no-image.png"
                        )
                    ).collect(Collectors.toList())
                : null;

        List<NFTCvCareerDto> careerList =
            (cvInfoDto.getCarr() != null)
                ? cvInfoDto.getCarr().stream().map(
                    it ->
                        new NFTCvCareerDto(
                            it.getTnofDvsnCode(),
                            it.getIsrnCmpyNm(),
                            it.getFpApmnDate(),
                            it.getFpFireDate()
                        )
                    ).collect(Collectors.toList())
                : null;

        List<NFTCvQualificationsDto> qualificationList =
            (cvInfoDto.getFpQlfcInfo() != null)
                ? cvInfoDto.getFpQlfcInfo().stream().map(
                    it ->
                        new NFTCvQualificationsDto(
                            it.getFpQlfcDvsnNm(), // 자격구분명
                            "", // 발급 기관명
                            it.getFpQlfcDvsnCode(), //
                            it.getFpQlfcAcqsDate() // 취득일
                        )
                    ).collect(Collectors.toList())
                : null;

        EmployeeContractInfoResponseDto cvContractInfo =
            (cvInfoDto.getCntcCrst() == null)
                ? null
                : cvInfoDto.getCntcCrst().get(0);

        NFTCvContractInfoDto contractInfo =
            (cvContractInfo != null)
                ? new NFTCvContractInfoDto(
                        cvContractInfo.getPoseCustCont(),
                        cvContractInfo.getPoseCntcCont(),
                        cvContractInfo.getCntcIfrt(),
                        cvContractInfo.getImpfSaleCont()
                    )
                : new NFTCvContractInfoDto("0", "0", "양호", "0");

        return new NFTCVRequestDto(
            callbackUrl,
            StringUtils.isEmpty(cvInfoDto.getClpsNm()) ? "FP" : cvInfoDto.getClpsNm(),
            cvInfoDto.getBrocOrgnNm(),
            cvInfoDto.getTnofDvsnCode(),
            consultingCompanyList,
            contractInfo, // contract status
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            careerList,
            qualificationList
        );
    }

    public static NFTCVRequestDto of(String callbackUrl, UserBean bean) {

        List<NFTCvConsultingCompanyDto> consultingCompanyList =
            (bean.getInsuranceList() != null)
                ? bean.getInsuranceList().stream().map(
                        it ->
                            new NFTCvConsultingCompanyDto(
                                it.getInsuranceCompanyName(),
                                it.getInsuranceCompanyCode().contains("L") ? InsuranceType.LIFE.getValue() : InsuranceType.GENERAL.getValue(),
                                "/img/no-image.png"
                            )
                    ).collect(Collectors.toList())
                : null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<NFTCvCareerDto> careerList =
            (bean.getCareerList() != null)
                ? bean.getCareerList().stream().map(it ->
                        new NFTCvCareerDto(
                            it.getPolicyholderCode(),
                            it.getCareerName(),
                            (it.getStartDate() != null) ? formatter.format(it.getStartDate()) : null,
                            (it.getEndDate() != null) ? formatter.format(it.getEndDate()) : null
                        )
                    ).collect(Collectors.toList())
                : null;

        List<NFTCvQualificationsDto> qualificationList =
            (bean.getQualificationList() != null)
                ? bean.getQualificationList().stream().map(it ->
                        new NFTCvQualificationsDto(
                            it.getQualificationName(), // 자격구분명
                            StringUtils.stripToEmpty(it.getQualificationOrganizationName()), // 발급 기관명
                            it.getQualificationCode(), // 자격구부코드
                            (it.getFinalQualifiedDate() != null) ? formatter.format(it.getFinalQualifiedDate()) : null // 취득일
                        )
                    ).collect(Collectors.toList())
                : null;

        ECardBean ecard = bean.getEcard();
        NFTCvContractInfoDto contractInfo =
            (ecard != null)
                ? NFTCvContractInfoDto.of(
                    ContractInfoDto.of(ecard)
                )
                : new NFTCvContractInfoDto("0", "0", "양호", "0");

        return new NFTCVRequestDto(
            callbackUrl,
            (ecard != null && StringUtils.isNotEmpty(ecard.getPosition())) ? ecard.getPosition() : "FP",
            (ecard != null && StringUtils.isNotEmpty(ecard.getDepartment())) ? ecard.getDepartment() : "소속 없음",
            (ecard != null && StringUtils.isNotEmpty(ecard.getOnDuty())) ? ecard.getOnDuty() : "01",
            consultingCompanyList,
            contractInfo, // contract status
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            careerList,
            qualificationList
        );
    }

    public NFTCVRequestDto(
        String callbackUri,
        String position,
        String branchOffice,
        String currentlyEmployed
    ) {
        this.callbackUri = callbackUri;
        this.position = position;
        this.branchOffice = branchOffice;
        this.currentlyEmployed = currentlyEmployed;
    }

    public NFTCVRequestDto(
        String callbackUri,
        String position,
        String branchOffice,
        String currentlyEmployed,
        List<NFTCvConsultingCompanyDto> consultingCompanyList,
        NFTCvContractInfoDto contractStatus,
        List<NFTCvGoodFpRwpDto> goodFPAwardList,
        List<NFTCvEducationDto> trainingList,
        List<NFTCvAwardDto> awardsList,
        List<NFTCvCareerDto> careerList,
        List<NFTCvQualificationsDto> licenseList
    ) {
        this.callbackUri = callbackUri;
        this.position = position;
        this.branchOffice = branchOffice;
        this.currentlyEmployed = currentlyEmployed;
        this.consultingCompanyList = consultingCompanyList;
        this.contractStatus = contractStatus;
        this.goodFPAwardList = goodFPAwardList;
        this.trainingList = trainingList;
        this.awardsList = awardsList;
        this.careerList = careerList;
        this.licenseList = licenseList;
    }

    @Override
    public Map<String, Object> convertToMap() {
        Map<String, Object> inputItem = new HashMap<>();

        inputItem.put("callbackUri", callbackUri);
        inputItem.put("position", position);
        inputItem.put("branchOffice", branchOffice);
        inputItem.put("currentlyEmployed", currentlyEmployed);

        inputItem.put("consultingCompanyList", consultingCompanyList);
        inputItem.put("contractStatus", contractStatus);
        inputItem.put("goodFPAwardList", goodFPAwardList);
        inputItem.put("trainingList", trainingList);
        inputItem.put("awardsList", awardsList);
        inputItem.put("careerList", careerList);
        inputItem.put("licenseList", licenseList);

        return inputItem;
    }
}
