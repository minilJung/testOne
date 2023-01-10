package com.ebc.ecard.application.badge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 뱃지 - 2022.06.23
 *
 * @author jgpark
 */
@NoArgsConstructor
@Getter
@Setter
public class EmployeeCvBadgeDto {
    protected String bdgeNm;        // 배지명
    protected String bdgeDvsnCode;  // 배지구분코드
    protected String valdStarDate;  // 유효시작일자
    protected String valdEndDate;   // 유효종료일자
    protected String bdgeCntn;      // 배지내용
    protected String bdgeCtfnNo;    // 배지인증번호
}
