package com.ebc.ecard.application.scraping.service;

import com.ebc.ecard.application.scraping.dto.PopupRequestFormDataDto;
import com.ebc.ecard.application.scraping.dto.ScrapingStatusInsightDto;
import com.ebc.ecard.domain.scraping.value.ScrapingType;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface UserIdentificationService {
	ScrapingStatusInsightDto getScrapingStatus(String scrapingId);
	PopupRequestFormDataDto buildScrapingRequestDto(String serverName, String userId, ScrapingType type)
		throws JsonProcessingException, InvalidAlgorithmParameterException, UnsupportedEncodingException,
		NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
		InvalidKeyException;

}