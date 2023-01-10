package com.ebc.ecard.application.ecard.dto;

import com.ebc.ecard.domain.ecard.ECardBean;
import com.ebc.ecard.domain.ecard.ECardTemplateBean;
import com.ebc.ecard.domain.user.UserBean;

import java.text.SimpleDateFormat;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardProfileContactUpdateDto {

    protected String ecardId;

    protected String profileFileId;
    protected String branchNumber;
    protected String faxNumber;

}
