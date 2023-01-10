package com.ebc.ecard.application.activity.dto.rwps;

import java.util.List;

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
public class EmployeeRwpsDto {
    protected String fpId;
    protected List<RwpsDto> rwpsList;

}
