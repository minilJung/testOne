package com.ebc.ecard.application.ecard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ECardCompletionPropertiesDto {

    protected boolean profileImageUploaded = false;
    protected boolean majoritySelected = false;
    protected boolean careerAdded = false;
    protected boolean qualificationAdded = false;
    protected boolean qualificationImageAdded = false;  //registration 변수명 오류로 같은 탭인 자격증 변수명 사용
}
