package com.ebc.ecard.application.ecard.dto;

import lombok.Getter;
import java.text.SimpleDateFormat;

@Getter
public class ECardBadgeDto {
    private String badgeId;
    private String userId;
    private String badgeName;
    private String badgeImageUrl;
    private String badgeDarkImageUrl;
    private String createdAt;
    private String publicYn;

    public static ECardBadgeDto ofEntity(ECardBadgeDto dto) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        return new ECardBadgeDto(
                dto.getBadgeId(),
                dto.getUserId(),
                dto.getBadgeName(),
                dto.getBadgeImageUrl(),
                dto.getBadgeDarkImageUrl(),
                formatter.format(dto.getCreatedAt()),
                dto.getPublicYn()
        );
    }

    public ECardBadgeDto(
        String badgeId,
        String userId,
        String badgeName,
        String badgeImageUrl,
        String badgeDarkImageUrl,
        String createdAt,
        String publicYn
    ) {
        this.badgeId = badgeId;
        this.userId = userId;
        this.badgeName = badgeName;
        this.badgeImageUrl = badgeImageUrl;
        this.badgeDarkImageUrl = badgeDarkImageUrl;
        this.createdAt = createdAt;
        this.publicYn = publicYn;
    }
}
