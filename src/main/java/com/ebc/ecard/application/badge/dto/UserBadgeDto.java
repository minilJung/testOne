package com.ebc.ecard.application.badge.dto;

import com.ebc.ecard.domain.badge.UserBadgeBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 뱃지 - 2022.06.23
 * @author jgpark
 */
@NoArgsConstructor
@Getter
@Setter
public class UserBadgeDto {
    protected String badgeId;
    protected String badgeName;
    protected String badgePeriod;
    protected String badgeImageUrl;
    protected String publicYn;

    public static UserBadgeDto fromBean(UserBadgeBean bean) {
        String badgeName = bean.getBadgeName();
        String name = badgeName;
        String period = "";
        if (badgeName.contains("(") && badgeName.contains(")")) {
            name = badgeName.substring(0, badgeName.indexOf("("));
            period = badgeName.substring(badgeName.indexOf("(") + 1, badgeName.indexOf(")"));
        }

        return new UserBadgeDto(
            bean.getBadgeId(),
            name,
            period,
            bean.getBadgeImageUrl(),
            bean.getPublicYn()
        );
    }

    public UserBadgeDto(String badgeId, String badgeName, String badgePeriod, String badgeImageUrl, String publicYn) {
        this.badgeId = badgeId;
        this.badgeName = badgeName;
        this.badgePeriod = badgePeriod;
        this.badgeImageUrl = badgeImageUrl;
        this.publicYn = publicYn;
    }
}
