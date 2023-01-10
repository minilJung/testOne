package com.ebc.ecard.application.nftcv.service;

import com.ebc.ecard.application.nftcv.dto.NFTCvResponseDto;
import com.ebc.ecard.application.nftcv.dto.request.NFTCVRequestDto;
import com.ebc.ecard.util.ReturnMessage;

import java.util.concurrent.CompletableFuture;

/**
 * @apiNote 서비스 이외 계층에서 참조 금지.
 * [Controller -> Service -> (InternalService) -> Mapper]
 */
public interface NftCvInternalService {

	ReturnMessage saveEBAPAccount(String accountId);
	CompletableFuture<ReturnMessage> saveOrUpdateUserCV(NFTCVRequestDto updateDto, String accountId);
	NFTCvResponseDto getUserCV(String accountId) throws Exception;

}