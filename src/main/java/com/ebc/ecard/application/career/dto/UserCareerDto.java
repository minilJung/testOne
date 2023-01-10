package com.ebc.ecard.application.career.dto;

import com.ebc.ecard.domain.career.UserCareerBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCareerDto {

    protected String careerId;
    protected String userId;
    protected String careerName;
    protected String startDate;
    protected String endDate;
    protected int workDays;
    protected String careerDescription;
    protected String publicYn;

    public static UserCareerDto fromBean(UserCareerBean bean) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");

        Date workEndDate = bean.getEndDate();
        if (workEndDate == null) {
            workEndDate = new Date();
        }

        long difference = bean.getStartDate().getTime() - workEndDate.getTime();
        int days = Math.abs((int) (difference / (60 * 60 * 24 * 1000)));

        String careerDescription;
        if ("Y".equals(bean.getOnDutyYn())) {
            careerDescription = "재직중";
        } else {
            int months = (days / 30) % 12;
            int years = (days / 30) / 12;

            careerDescription = ((years > 0) ? years + "년 " : "") + months + "개월";
        }

        return new UserCareerDto(
            bean.getCareerId(),
            bean.getUserId(),
            bean.getCareerName(),
            format.format(bean.getStartDate()),
            (bean.getEndDate() == null) ? null : format.format(bean.getEndDate()),
            days,
            careerDescription,
            bean.getPublicYn()
        );
    }

    public UserCareerDto(
        String careerId,
        String userId,
        String careerName,
        String startDate,
        String endDate,
        int workDays,
        String careerDescription,
        String publicYn
    ) {
        this.careerId = careerId;
        this.userId = userId;
        this.careerName = careerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workDays = workDays;
        this.careerDescription = careerDescription;
        this.publicYn = publicYn;
    }
}