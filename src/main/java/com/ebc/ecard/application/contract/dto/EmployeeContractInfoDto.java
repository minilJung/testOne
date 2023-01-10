package com.ebc.ecard.application.contract.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EmployeeContractInfoDto {
    protected String fpId;
    protected ContractInfoDto cntcInfo;
}
