package com.ebc.ecard.application.checkplus.dto;

import com.ebc.ecard.application.dto.ResponseDtoInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckplusTokenResponseDataBodyDto implements ResponseDtoInterface {
    protected String rsp_cd;
    protected String res_msg;
    protected String result_cd;
    protected String site_code;
    protected String token_version_id;
    protected String token_val;
    protected String period;
}

