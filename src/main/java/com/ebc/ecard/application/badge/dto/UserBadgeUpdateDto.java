package com.ebc.ecard.application.badge.dto;

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
public class UserBadgeUpdateDto {
    protected String badgeId;
    protected String publicYn;

}
