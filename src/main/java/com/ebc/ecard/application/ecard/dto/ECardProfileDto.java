package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.domain.ecard.ECardTemplateBean;
import com.ebc.ecard.domain.user.UserBean;

import java.text.SimpleDateFormat;
import java.util.List;

import lombok.Getter;

@Getter
public class ECardProfileDto {
    protected String ecardId;
    protected String profileImageUrl;
    protected String previewImageUrl;
    protected String ecardChangedYn;
    protected String branchName;
    protected String employeeName;
    protected String position;
    protected String email;
    protected String birthdate;
    protected String onDuty;
    protected String branchNumber;
    protected String mobileNumber;
    protected String faxNumber;
    protected String profileBgUrl;
    protected String branchNumberPublicYn;
    protected String faxNumberPublicYn;
    protected String badgePublicYn;
    protected List<ECardBadgeDto> badgeList;
    protected ECardTemplateDto template;

    public static ECardProfileDto of(ECardBean ecard, UserBean user, ECardTemplateBean templateBean, List<ECardBadgeDto> badgeList)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new ECardProfileDto(
            ecard.getEcardId(),
            ecard.getProfileImageUrl(),
            ecard.getPreviewImageUrl(),
            ecard.getEcardChangedYn(),
            ecard.getDepartment(),
            user.getName(),
            ecard.getPosition(),
            ecard.getEmail(),
            user.getBirthdate() == null ? null : formatter.format(user.getBirthdate()),
            ecard.getOnDuty(),
            ecard.getBranchNumber(),
            user.getMobileNo(),
            ecard.getFaxNumber(),
            ecard.getProfileImageUrl(),
            ecard.getBranchNumberPublicYn(),
            ecard.getFaxNumberPublicYn(),
            ecard.getBadgePublicYn(),
            badgeList,
            (templateBean == null) ? null : ECardTemplateDto.fromEntity(templateBean)
        );
    }

    public ECardProfileDto(
            String ecardId,
            String profileImageUrl,
            String previewImageUrl,
            String ecardChangedYn,
            String branchName,
            String employeeName,
            String position,
            String email,
            String birthdate,
            String onDuty,
            String branchNumber,
            String mobileNumber,
            String faxNumber,
            String profileBgUrl,
            String branchNumberPublicYn,
            String faxNumberPublicYn,
            String badgePublicYn,
            List<ECardBadgeDto> badgeList,
            ECardTemplateDto template
    ) {
        this.ecardId = ecardId;
        this.profileImageUrl = profileImageUrl;
        this.previewImageUrl = previewImageUrl;
        this.ecardChangedYn = ecardChangedYn;
        this.branchName = branchName;
        this.employeeName = employeeName;
        this.position = position;
        this.email = email;
        this.birthdate = birthdate;
        this.onDuty = onDuty;
        this.branchNumber = branchNumber;
        this.mobileNumber = mobileNumber;
        this.faxNumber = faxNumber;
        this.profileBgUrl = profileBgUrl;
        this.branchNumberPublicYn = branchNumberPublicYn;
        this.faxNumberPublicYn = faxNumberPublicYn;
        this.badgePublicYn = badgePublicYn;
        this.badgeList = badgeList;
        this.template = template;
    }

}
