package com.ebc.ecard.domain.insurance;

import com.ebc.ecard.application.insurance.dto.EmployeeCvInsuranceResponseDto;
import com.ebc.ecard.domain.insurance.value.InsuranceType;
import com.ebc.ecard.util.BaseBean;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInsuranceBean extends BaseBean {

    protected String insuranceId;
    protected String userId;
    protected String insuranceCompanyCode;
    protected String insuranceCompanyName;
    protected String insuranceCompanyLogo;
    protected InsuranceType insuranceType;
    protected String publicYn;
    protected Date createdAt;

    public static UserInsuranceBean create(String insuranceId, String userId, EmployeeCvInsuranceResponseDto insurance) {

        String insuranceType = String.valueOf(insurance.getIscmCode().charAt(0));

        return new UserInsuranceBean(
            insuranceId,
            userId,
            insurance.getIscmCode(),
            insurance.getIscmNm(),
            "",
            !insuranceType.equals("L") ? InsuranceType.GENERAL : InsuranceType.LIFE,
            "Y",
            new Date()
        );
    }

    public UserInsuranceBean(
            String insuranceId,
            String userId,
            String insuranceCompanyCode,
            String insuranceCompanyName,
            String insuranceCompanyLogo,
            InsuranceType insuranceType,
            String publicYn,
            Date createdAt
    ) {
        this.insuranceId = insuranceId;
        this.userId = userId;
        this.insuranceCompanyCode = insuranceCompanyCode;
        this.insuranceCompanyName = insuranceCompanyName;
        this.insuranceCompanyLogo = insuranceCompanyLogo;
        this.insuranceType = insuranceType;
        this.publicYn = publicYn;
        this.createdAt = createdAt;
    }
}