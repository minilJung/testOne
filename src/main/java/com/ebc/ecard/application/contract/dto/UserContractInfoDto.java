package com.ebc.ecard.application.contract.dto;

import com.ebc.ecard.application.ecard.dto.ECardContractSettingsDto;
import com.ebc.ecard.domain.ecard.ECardBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserContractInfoDto {

    protected ECardContractSettingsDto settings;

    protected ContractInfoDto contractInfo;

    public static UserContractInfoDto of(ECardBean bean) {
        return new UserContractInfoDto(
            ECardContractSettingsDto.of(bean),
            ContractInfoDto.of(bean)
        );
    }

    public UserContractInfoDto(
        ECardContractSettingsDto settings,
        ContractInfoDto contractInfo
    ) {
        this.settings = settings;
        this.contractInfo = contractInfo;
    }
}
