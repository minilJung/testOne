package com.ebc.ecard.application.nftcv.dto;

import com.ebc.ecard.application.dto.ResponseDtoInterface;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NFTCvResponseDto implements ResponseDtoInterface {

    private NFTCvDto data;
}
