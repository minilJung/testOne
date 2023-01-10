package com.ebc.ecard.application.activity.dto.rwps;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 수상 내역 - 2022.06.23
 * @author jgpark
 */
@NoArgsConstructor
@Getter
@Setter
public class RwpsDto {
    protected String rwpsDvsnCode;
    protected String rwpsBrocNm;
    protected String rwpsCntnNm;
    protected String rwpsStarDate;

}
