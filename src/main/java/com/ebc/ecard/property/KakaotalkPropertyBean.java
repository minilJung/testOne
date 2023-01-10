package com.ebc.ecard.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ConfigurationProperties("ecard.services.kakao")
@Getter
@Setter
public class KakaotalkPropertyBean {

	private String appId;
	private String jsKey;

}