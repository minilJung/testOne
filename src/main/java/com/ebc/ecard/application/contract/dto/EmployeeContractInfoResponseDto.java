package com.ebc.ecard.application.contract.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeContractInfoResponseDto {
    protected String cntcIfrt;
    protected String poseCustCont;
    protected String impfSaleCont;
    protected String poseCntcCont;
}
