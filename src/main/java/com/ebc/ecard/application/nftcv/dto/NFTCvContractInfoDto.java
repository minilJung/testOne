package com.ebc.ecard.application.nftcv.dto;

import com.ebc.ecard.application.contract.dto.ContractInfoDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * NFT CV 계약현황 생성 시 필요한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NFTCvContractInfoDto {
    protected String clientCnt;         // 보유 고객 수
    protected String contractCnt ;      // 보유 계약 수
    protected String retentionRateesc;  // 계약 유지율
    protected String misSellCnt;        // 불완전판매건수

    public static NFTCvContractInfoDto of(ContractInfoDto contractInfo) {

        return new NFTCvContractInfoDto(
            String.valueOf(contractInfo.getCustCnt()),
            String.valueOf(contractInfo.getCntcCnt()),
            String.valueOf(contractInfo.getCntcMaintainRate()),
            String.valueOf(contractInfo.getImperfectCntcCnt())
        );
    }

}
