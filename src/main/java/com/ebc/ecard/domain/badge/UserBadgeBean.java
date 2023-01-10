package com.ebc.ecard.domain.badge;

import com.ebc.ecard.application.badge.dto.EmployeeCvBadgeDto;
import com.ebc.ecard.util.BaseBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserBadgeBean extends BaseBean {
    protected String badgeId;
    protected String userId;
    protected String badgeName;
    protected String badgeImageUrl;
    protected String publicYn;
    protected Date createdAt;

    public static UserBadgeBean of(String badgeId, String userId, EmployeeCvBadgeDto badge) throws ParseException {
        SimpleDateFormat badgeFormatter = new SimpleDateFormat("yyyy-MM-dd");

        return new UserBadgeBean(
            badgeId,
            userId,
            "2".equals(badge.getBdgeDvsnCode()) ? "우수인증설계사" : "ACE_CLUB",
            "2".equals(badge.getBdgeDvsnCode()) ? "/img/badge/good_award_logo.svg" : "/img/badge/aceclub_logo.svg",
            "Y",
            badge.getValdStarDate().contains("9999") ? null : badgeFormatter.parse(badge.getValdStarDate())
        );
    }

    public UserBadgeBean(
            String badgeId,
            String userId,
            String badgeName,
            String badgeImageUrl,
            String publicYn,
            Date createdAt
    ) {
        this.badgeId = badgeId;
        this.userId = userId;
        this.badgeName = badgeName;
        this.badgeImageUrl = badgeImageUrl;
        this.publicYn = publicYn;
        this.createdAt = createdAt;
    }
}