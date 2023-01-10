package com.ebc.ecard.application.scraping.service;

import com.ebc.ecard.application.scraping.builder.NHISLoginRequestDtoBuilder;
import com.ebc.ecard.application.scraping.builder.QNETLoginRequestDtoBuilder;
import com.ebc.ecard.application.scraping.dto.PopupRequestFormDataDto;
import com.ebc.ecard.application.scraping.dto.ScrapingStatusInsightDto;
import com.ebc.ecard.application.user.dto.CooconIdentificationPayloadDto;
import com.ebc.ecard.domain.scraping.ScrapingBean;
import com.ebc.ecard.domain.scraping.value.ScrapingType;
import com.ebc.ecard.mapper.ScrapingMapper;
import com.ebc.ecard.mapper.UserMapper;
import com.ebc.ecard.property.CooconPropertyBean;
import com.ebc.ecard.util.AESUtil;
import com.ebc.ecard.util.XeCommon;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserIdentificationServiceImpl implements UserIdentificationService {

	@Value("${ecard.services.coocon.publishing-key}")
	protected String PUBLISHING_KEY;

	@Value("${ecard.services.coocon.org-code}")
	protected String ORG_CODE;

	@Resource
	private CooconPropertyBean cooconProperty;

	@Resource
	protected ObjectMapper objectMapper;

	@Resource
	protected UserMapper userMapper;

	@Resource
	protected ScrapingMapper scrapingMapper;

	@Resource
	XeCommon common;

	@Override
	public ScrapingStatusInsightDto getScrapingStatus(String scrapingId) {
		ScrapingBean bean = scrapingMapper.findScrapingByScrapingId(scrapingId);
		if (bean == null) {
			return null;
		}

		return ScrapingStatusInsightDto.from(bean);
	}

	@Override
	public PopupRequestFormDataDto buildScrapingRequestDto(String serverName, String userId, ScrapingType type)
		throws JsonProcessingException,
		InvalidAlgorithmParameterException,
		UnsupportedEncodingException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {

		CooconIdentificationPayloadDto userInfo = userMapper.selectScrapingPayload(userId);

		String birthDate = userInfo.getBirthdate();
		if (StringUtils.isNotEmpty(birthDate)) {
			birthDate = birthDate.replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z\\\\s]", "");
			userInfo.setBirthdate(birthDate);
		}

		String scrapingId = common.getUuid(false);


		Map<String, Object> body = null;

		if (type.equals(ScrapingType.CAREER)) {
			body = buildNhisScrapingRequestDto(scrapingId, serverName, userId, userInfo);
		}

		if (type.equals(ScrapingType.LICENSE)) {
			body = buildQnetScrapingRequestDto(scrapingId, serverName, userId, userInfo);
		}

		String requestDataJsonString = objectMapper.writeValueAsString(body);

		log.info("Scraping Request type: {}, Data: {}", type.name(), requestDataJsonString);

		String requestData = AESUtil.encryptAesToBase64(requestDataJsonString, PUBLISHING_KEY);
		String requestVid = AESUtil.getHmacSHA256ToStr(PUBLISHING_KEY, requestDataJsonString);

		return startScraping(scrapingId, userInfo.getEcardId(), requestData, requestVid, type.name());
	}

	public Map<String, Object> buildQnetScrapingRequestDto(
		String scrapingId,
		String serverName,
		String userId,
		CooconIdentificationPayloadDto payload
	) {

		return new QNETLoginRequestDtoBuilder(
			serverName + "/api/users/" + userId + "/qnet/callback", ""
		).build(payload, scrapingId);
	}

	public Map<String, Object> buildNhisScrapingRequestDto(
		String scrapingId,
		String serverName,
		String userId,
		CooconIdentificationPayloadDto payload
	) {
		return new NHISLoginRequestDtoBuilder(
			serverName + "/api/users/" + userId + "/nhis/callback", ""
		).build(payload, scrapingId);
	}

	private PopupRequestFormDataDto startScraping(
		String scrapingId,
		String ecardId,
		String requestData,
		String requestVid,
		String scrapingType
	) throws JsonProcessingException {
		PopupRequestFormDataDto requestDto = new PopupRequestFormDataDto(
			requestData,
			requestVid,
			ORG_CODE,
			scrapingId,
			cooconProperty.getUrlWithProtocol()
		);

		scrapingMapper.saveScrapingData(
			new ScrapingBean(
				scrapingId,
				ecardId,
				objectMapper.writeValueAsString(requestDto),
				null,
				null,
				"0",
				scrapingType,
				new Date(),
				null
			)
		);

		return requestDto;
	}
}