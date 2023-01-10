package com.ebc.ecard.application.user.internal;

import com.ebc.ecard.application.user.dto.IAMRequestDto;
import com.ebc.ecard.application.user.dto.IAMResponseDto;

/**
 * @apiNote 서비스 이외 계층에서 참조 금지.
 * [Controller -> Service -> (InternalService) -> Mapper]
 */
public interface IAMInternalService {

	IAMResponseDto saveIAM(IAMRequestDto params);

}