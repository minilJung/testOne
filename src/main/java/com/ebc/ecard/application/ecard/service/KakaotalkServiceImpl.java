package com.ebc.ecard.application.ecard.service;

import com.ebc.ecard.property.KakaotalkPropertyBean;
import com.ebc.ecard.application.ecard.dto.KakaotalkApiKeyDto;
import com.ebc.ecard.util.AES256;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KakaotalkServiceImpl implements KakaotalkService {

	@Resource
	KakaotalkPropertyBean kakaotalkProperties;

	public KakaotalkApiKeyDto getKakaotalkApiKey() {

		try {
			if (kakaotalkProperties != null) {
				return new KakaotalkApiKeyDto(
					AES256.encrypt(kakaotalkProperties.getAppId(), AES256.EBC_AES256_KEY),
					AES256.encrypt(kakaotalkProperties.getJsKey(), AES256.EBC_AES256_KEY)
				);
			}
		} catch(Exception e) {
			log.info("Error occurred while get kakaotalk properties", e);
		}

		return null;
	}
}