package com.ebc.ecard.application.contract.dto;

import com.ebc.ecard.domain.ecard.ECardBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContractInfoDto {
    protected int custCnt;              //고객 수
    protected int cntcCnt;              //계약 수
    protected int imperfectCntcCnt;     //불완전판매 수
    protected float cntcMaintainRate;  //계약 유지율

    public static ContractInfoDto of(EmployeeContractInfoResponseDto cvContractResponse) {
        String custCnt = cvContractResponse.getPoseCustCont();
        String cntcCnt = cvContractResponse.getPoseCntcCont();
        String imperfectCntcCnt = cvContractResponse.getImpfSaleCont();
        String cntcMaintainRate = cvContractResponse.getCntcIfrt();

        return new ContractInfoDto(
            (custCnt != null) ? Integer.parseInt(custCnt) : 0,
            (cntcCnt != null) ? Integer.parseInt(cntcCnt) : 0,
            (imperfectCntcCnt != null) ? Integer.parseInt(imperfectCntcCnt) : 0,
            (cntcMaintainRate != null) ? Float.parseFloat(cntcMaintainRate) : 0
        );
    }

    public static ContractInfoDto of(ECardBean bean) {

        return new ContractInfoDto(
            bean.getCustomerCounts(),
            bean.getContractCounts(),
            bean.getImperfectCounts(),
            bean.getContractRate()
        );
    }

    public ContractInfoDto(int custCnt, int cntcCnt, int imperfectCntcCnt, float cntcMaintainRate) {
        this.custCnt = custCnt;
        this.cntcCnt = cntcCnt;
        this.imperfectCntcCnt = imperfectCntcCnt;
        this.cntcMaintainRate = cntcMaintainRate;
    }

}
