package com.ebc.ecard.domain.qualification;

import com.ebc.ecard.application.qualification.dto.EmployeeCvQlfcResponseDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserQualificationBean {
    protected String qualificationId;
    protected String userId;
    protected String qualificationName;
    protected String qualificationCode;
    protected String qualificationNumber;
    protected String qualificationOrganizationName;
    protected Date finalQualifiedDate;
    protected String publicYn;
    protected String scrapingStatus;

    public static UserQualificationBean of(
        String qualificationId,
        String userId,
        EmployeeCvQlfcResponseDto qlfc
    ) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return new UserQualificationBean(
            qualificationId,
            userId,
            qlfc.getFpQlfcDvsnNm(),
            "",
            "",
            qlfc.getFpQlfcAcqsDate().contains("9999") ? null : formatter.parse(qlfc.getFpQlfcAcqsDate()),
            "Y"
        );
    }

    public UserQualificationBean(
        String qualificationId,
        String userId,
        String qualificationName,
        String qualificationNumber,
        String qualificationOrganizationName,
        Date finalQualifiedDate,
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

    public UserQualificationBean(
            String qualificationId,
            String userId,
            String qualificationName,
            String qualificationNumber,
            String qualificationOrganizationName,
            Date finalQualifiedDate,
            String publicYn,
            String scrapingStatus
    ) {
        this.qualificationId = qualificationId;
        this.userId = userId;
        this.qualificationName = qualificationName;
        this.qualificationNumber = qualificationNumber;
        this.qualificationOrganizationName = qualificationOrganizationName;
        this.finalQualifiedDate = finalQualifiedDate;
        this.publicYn = publicYn;
        this.scrapingStatus = scrapingStatus;
    }
}
