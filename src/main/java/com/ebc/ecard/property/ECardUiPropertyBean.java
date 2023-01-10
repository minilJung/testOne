package com.ebc.ecard.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("ecard.services.ecard")
@Getter
@Setter
public class ECardUiPropertyBean {

	private String url;

	public String getUrlWithProtocol() {
		return url.contains("localhost")
				? "http://" + url
				: "https://" + url;
	}

}