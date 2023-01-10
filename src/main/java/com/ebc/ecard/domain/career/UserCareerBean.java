package com.ebc.ecard.domain.career;

import com.ebc.ecard.application.career.dto.EmployeeCvCareerInfoResponseDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class UserCareerBean{

    protected String careerId;
    protected String userId;
    protected String policyholderCode; // 가입자 구분
    protected String careerName;
    protected Date startDate;
    protected Date endDate;
    protected String onDutyYn;
    protected String publicYn;
    protected String scrapingStatus;

    public static UserCareerBean of(
        String careerId,
        String userId,
        EmployeeCvCareerInfoResponseDto career
    ) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return new UserCareerBean(
            careerId,
            userId,
            "",
            career.getIsrnCmpyNm(),
            StringUtils.isNotEmpty(career.getFpApmnDate()) ? formatter.parse(career.getFpApmnDate()) : null,
            (StringUtils.isEmpty(career.getFpFireDate()) || career.getFpFireDate().contains("9999") ) ? null : formatter.parse(career.getFpFireDate()),
            !career.getTnofDvsnCode().equals("10") ? "N" : "Y",
            "Y"
        );
    }

    public UserCareerBean(
        String careerId, String userId, String policyholderCode, String careerName, Date startDate, Date endDate, String onDutyYn, String publicYn
    ) {
        this.careerId = careerId;
        this.userId = userId;
        this.policyholderCode = policyholderCode;
        this.careerName = careerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.onDutyYn = onDutyYn;
        this.publicYn = publicYn;
    }

    public UserCareerBean(
            String careerId, String userId, String policyholderCode, String careerName, Date startDate, Date endDate, String onDutyYn, String publicYn, String scrapingStatus
    ) {
        this.careerId = careerId;
        this.userId = userId;
        this.policyholderCode = policyholderCode;
        this.careerName = careerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.onDutyYn = onDutyYn;
        this.publicYn = publicYn;
        this.scrapingStatus = scrapingStatus;
    }

    public boolean isEqualsToCvCareer(EmployeeCvCareerInfoResponseDto cvCareer) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String startDate = cvCareer.getFpApmnDate();
        return this.getCareerName().equals(cvCareer.getIsrnCmpyNm()) // 경력명
            && formatter.format(this.getStartDate()).equals(startDate); // 입사일
    }

    public boolean isEqualsTo(UserCareerBean career) {
        return this.getCareerName().equals(career.getCareerName()) // 경력명
            && this.startDate.getTime() == career.startDate.getTime(); // 입사일
    }
}
