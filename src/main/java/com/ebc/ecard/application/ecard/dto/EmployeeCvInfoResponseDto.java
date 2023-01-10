package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.application.badge.dto.EmployeeCvBadgeDto;
import com.ebc.ecard.application.career.dto.EmployeeCvCareerInfoResponseDto;
import com.ebc.ecard.application.contract.dto.EmployeeContractInfoResponseDto;
import com.ebc.ecard.application.dto.ResponseDtoInterface;
import com.ebc.ecard.application.insurance.dto.EmployeeCvInsuranceResponseDto;
import com.ebc.ecard.application.qualification.dto.EmployeeCvQlfcResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeCvInfoResponseDto implements ResponseDtoInterface {

    protected String retnCode;
    protected String fxno;
    protected String brocOrgnCode;
    protected String brocOrgnNm;
    protected String tlno;
    protected String erroMesg;
    protected String fpUniqNo;
    protected String tnofDvsnCode;
    protected String ctsrNbyr;
    protected String mailAddr;
    protected String erroCode;
    protected String hpno;
    protected String fpNm;
    protected String clpsNm;
    protected List<EmployeeCvQlfcResponseDto> fpQlfcInfo;
    protected List<EmployeeContractInfoResponseDto> cntcCrst;
    protected List<EmployeeCvCareerInfoResponseDto> carr;
    protected List<EmployeeCvInsuranceResponseDto> cuslPssbIscm;
    protected List<EmployeeCvBadgeDto> bdge;



}
