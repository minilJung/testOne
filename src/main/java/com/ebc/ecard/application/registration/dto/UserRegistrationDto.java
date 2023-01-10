package com.ebc.ecard.application.registration.dto;

import com.ebc.ecard.domain.qualification.UserRegistrationBean;
import com.ebc.ecard.util.XeCommon;

import java.text.SimpleDateFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    protected String registrationId;
    protected String userId;
    protected String registrationFileId;
    protected String fileName;
    protected String filePath;
    protected String publicYn;
    protected String createdAt;
    protected String lastUpdatedAt;

    public UserRegistrationDto(
        String registrationId,
        String userId,
        String registrationFileId,
        String fileName,
        String filePath,
        String publicYn,
        String createdAt,
        String lastUpdatedAt
    ) {
        this.registrationId = registrationId;
        this.userId = userId;
        this.registrationFileId = registrationFileId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.publicYn = publicYn;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public static UserRegistrationDto from(XeCommon common, UserRegistrationBean bean) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String registrationImgUrl = common.getS3FilePath(bean.getFileName());

        return new UserRegistrationDto(
            bean.getRegistrationId(),
            bean.getUserId(),
            bean.getRegistrationFileId(),
            bean.getFileName(),
            registrationImgUrl,
            bean.getPublicYn(),
            formatter.format(bean.getCreatedAt()),
            formatter.format(bean.getLastUpdatedAt())
        );
    }
}
