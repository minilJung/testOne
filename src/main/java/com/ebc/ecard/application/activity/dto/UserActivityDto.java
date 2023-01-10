package com.ebc.ecard.application.activity.dto;

import com.ebc.ecard.domain.activity.UserEducationBean;
import com.ebc.ecard.domain.activity.UserRwpsBean;
import com.ebc.ecard.domain.activity.value.ActivityType;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserActivityDto {

    protected String id;

    protected ActivityType type;

    protected String title;

    protected String organizationName;

    protected String startDate;

    protected String endDate;

    protected String publicYn;

    //protected String status;

    private Instant startDateOrigin;

    public static UserActivityDto fromBean(UserEducationBean bean) {

        return new UserActivityDto(
            bean.getEducationId(),
            ActivityType.EDUCATION,
            bean.getEducationName(),
            bean.getEducationOrganizationName(),
            bean.getStartDate(),
            bean.getEndDate(),
            bean.getPublicYn()
            //bean.getStatus().getValue(),
        );
    }

    public static UserActivityDto fromBean(UserRwpsBean bean) {

        return new UserActivityDto(
            bean.getRwpsId(),
            ActivityType.RWPS,
            bean.getRwpsCntnName(),
            bean.getRwpsBrocName(),
            bean.getRwpsStarDate(),
            null,
            bean.getPublicYn()
            //bean.getStatus().getValue(),
        );
    }

    public UserActivityDto(
            String id,
            ActivityType type,
            String title,
            String organizationName,
            Date startDate,
            Date endDate,
            String publicYn
            //String status,
    ) {
        String pattern = (type == ActivityType.EDUCATION)
            ? "yyyy.MM"
            : "yyyy.MM.dd";

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        this.id = id;
        this.type = type;
        this.title = title;
        this.organizationName = organizationName;
        this.startDate = formatter.format(startDate);
        this.startDateOrigin = startDate.toInstant();
        this.endDate = (endDate == null)
            ? null
            : formatter.format(endDate);
        this.publicYn = publicYn;
        //this.status = status;
    }
}
