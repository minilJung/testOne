package com.ebc.ecard.application.ecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerAccessCountDto {

    protected String fpId;
    protected String custId;
    protected int accessCounts;

    public CustomerAccessCountDto(String fpId, String custId, int accessCounts) {
        this.fpId = fpId;
        this.custId = custId;
        this.accessCounts = accessCounts;
    }
}
