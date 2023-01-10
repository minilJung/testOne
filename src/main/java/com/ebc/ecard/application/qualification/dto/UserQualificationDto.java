package com.ebc.ecard.application.qualification.dto;

import com.ebc.ecard.domain.qualification.UserQualificationBean;

import java.text.SimpleDateFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserQualificationDto {
    protected String qualificationId;
    protected String userId;
    protected String qualificationName;
    protected String qualificationNumber;
    protected String qualificationOrganizationName;
    protected String finalQualifiedDate;
    protected String publicYn;

    public static UserQualificationDto fromBean(UserQualificationBean bean) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        return new UserQualificationDto(
            bean.getQualificationId(),
            bean.getUserId(),
            bean.getQualificationName(),
            bean.getQualificationNumber(),
            bean.getQualificationOrganizationName(),
            formatter.format(bean.getFinalQualifiedDate()),
            bean.getPublicYn()
        );
    }

    public UserQualificationDto(
        String qualificationId,
        String userId,
        String qualificationName,
        String qualificationNumber,
        String qualificationOrganizationName,
        String finalQualifiedDate,
        String publicYn
    ) {
        this.qualificationId = qualificationId;
        this.userId = userId;
        this.qualificationName = qualificationName;
        this.qualificationNumber = qualificationNumber;
        this.qualificationOrganizationName = qualificationOrganizationName;
        this.finalQualifiedDate = finalQualifiedDate;
        this.publicYn = publicYn;
    }
}